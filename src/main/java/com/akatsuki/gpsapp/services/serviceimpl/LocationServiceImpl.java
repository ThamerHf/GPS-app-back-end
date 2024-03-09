package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TagResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import com.akatsuki.gpsapp.models.entity.TagEntity;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.repository.LocationRepository;
import com.akatsuki.gpsapp.repository.TagRepository;
import com.akatsuki.gpsapp.services.service.LocationService;
import com.akatsuki.gpsapp.services.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final UserService userService;

    private final TagRepository tagRepository;

    public LocationServiceImpl(LocationRepository locationRepository,
                               UserService userService, TagRepository tagRepository) {
        this.locationRepository = locationRepository;
        this.userService = userService;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<LocationResponseDto> getAllLocations() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Collections.emptyList();
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<UserEntity> userEntity = userService
                    .findByUserName(userDetails.getUsername());
            if(userEntity.isPresent()) {
                UserEntity userEntity1 = userEntity.get();
                List<LocationEntity> locations = this.locationRepository.findByUser(userEntity1);
                System.out.println("username" + userEntity1.getFirstName());
            System.out.println(locations);
            System.out.println("works");

            return locations.stream()
                        .map(this::mappingToLocationResponseDto)
                        .collect(Collectors.toList());
            }
        else {
            System.out.println("doesn't work");
            return null;
        }

    }

    @Override
    public LocationResponseDto createLocation(LocationRequestDto locationRequestDto) {
        LocationEntity locationEntity = mappingToLocationEntity(locationRequestDto);
        String username = this.userService.getAuthenticatedUser();
        Optional<UserEntity> userEntity = userService.findByUserName(username);
        if(userEntity.isPresent()) {
            UserEntity userEntityToSave = userEntity.get();
            locationEntity.setUser(userEntityToSave);
            System.out.println(locationEntity.getUser().getFirstName());

        }

        return this.mappingToLocationResponseDto(this.locationRepository
                .save(locationEntity));
    }

    @Override
    public void updateLocation(long id, LocationRequestDto locationRequestDto) throws CustomizedException {
        Optional<LocationEntity> locationEntityBddOpt = locationRepository.findById(String.valueOf(id));
        LocationEntity locationEntityRequest = mappingToLocationEntity(locationRequestDto);
        String username = this.userService.getAuthenticatedUser();
        Optional<UserEntity> userEntity = userService.findByUserName(username);
        if(locationEntityBddOpt.isPresent()) {
            locationEntityRequest.setLocationId(locationEntityBddOpt.get().getLocationId());
            locationRepository.save(locationEntityRequest);

        } else {
            throw new CustomizedException(ResponseMessage.LOCATION_NOT_FOUND.toString()
                    , HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<LocationEntity> getLocationById(long id){
        String userName = this.userService.getAuthenticatedUser();
        Optional<UserEntity> userEntity = userService.findByUserName(userName);
        if(userEntity.isPresent()) {
            UserEntity userEntity1 = userEntity.get();
            List<LocationEntity> locations = userEntity1.getLocations();
            return locations.stream().filter(location -> location.getLocationId().equals(id))
                    .findFirst();
        }else {
            return Optional.empty();
        }
    }

    public void deleteLocation(Long locationId) {
        Optional<LocationEntity> location = locationRepository.findByLocationId(locationId);
        String userName = this.userService.getAuthenticatedUser();
        Optional<UserEntity> userEntity = userService.findByUserName(userName);
        if (userEntity.isPresent() && location.isPresent()) {
            LocationEntity locationGot = location.get();
            UserEntity userEntity1 = userEntity.get();
            List<LocationEntity> locations = userEntity1.getLocations();
            if (locations.contains(locationGot)) {
                locationRepository.delete(locationGot);
            }
        }
    }

    private LocationEntity mappingToLocationEntity(LocationRequestDto locationRequestDto) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationId(locationRequestDto.getLocationId());
        locationEntity.setTitle(locationRequestDto.getTitle());
        locationEntity.setCoord(locationRequestDto.getCoord());
        locationEntity.setAdresse(locationRequestDto.getAdresse());
        locationEntity.setDescription(locationRequestDto.getDescription());
        locationEntity.setImage(locationRequestDto.getImage());

        return locationEntity;
    }

    private LocationResponseDto mappingToLocationResponseDto(LocationEntity locationEntity) {
        LocationResponseDto locationResponseDto = new LocationResponseDto();
        locationResponseDto.setLocationId(locationEntity.getLocationId());
        locationResponseDto.setTitle(locationEntity.getTitle());
        locationResponseDto.setCoord(locationEntity.getCoord());
        locationResponseDto.setAdresse(locationEntity.getAdresse());
        locationResponseDto.setDescription(locationEntity.getDescription());
        locationResponseDto.setImage(locationEntity.getImage());

        return locationResponseDto;
    }

    @Override
    public List<LocationResponseDto> getTag(Long tagId) throws CustomizedException {
        String userName = this.userService
                .getAuthenticatedUser();
        List<TagEntity> tagEntities = new ArrayList<>();
        Optional<TagEntity> tagEntity = this.tagRepository.findByTagId(tagId);
        if (tagEntity.isEmpty()) {
            throw new CustomizedException(ResponseMessage.TAG_NOT_FOUND.toString(),
                    HttpStatus.BAD_REQUEST);
        }

        List<LocationEntity> locationEntities = this.locationRepository
                .findByUser_UserNameAndTagId(userName, tagEntity.get().getTagId());
        List<LocationResponseDto> responseDtos = new ArrayList<>();
        for (LocationEntity location: locationEntities) {
            responseDtos.add(this.mappingToLocationResponseDto(location));
        }

        return responseDtos;
    }

    @Override
    public List<TagResponseDto> getTags() throws CustomizedException {
        List<TagResponseDto> ownedTags = this.getOwnedTags();
        List<TagResponseDto> sharedTags = this.getSharedTags();
        List<TagResponseDto> tags = new ArrayList<>();
        tags.addAll(ownedTags);
        tags.addAll(sharedTags);

        return tags;
    }

    @Override
    public List<TagResponseDto> getOwnedTags() throws CustomizedException {
        String userName = this.userService
                .getAuthenticatedUser();
        List<TagEntity> tagEntities = this.tagRepository
                .findTagsByUserName(userName);
        List<TagResponseDto> responseDtos = new ArrayList<>();

        for (TagEntity tag: tagEntities) {
            responseDtos.add(this.mappingToTagResponseDto(tag));
        }

        return responseDtos;
    }

    @Override
    public List<TagResponseDto> getSharedTags() throws CustomizedException {

        String userName = this.userService
                .getAuthenticatedUser();
        List<TagEntity> tagEntities = this.tagRepository
                .findTagsSharedWithUser(userName);
        List<TagResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(this.getOrphanLocations());

        for (TagEntity tag: tagEntities) {
            responseDtos.add(this.mappingToTagResponseDto(tag));
        }

        return responseDtos;
    }

    private TagResponseDto getOrphanLocations() {
        String userName = this.userService
                .getAuthenticatedUser();
        List<TagResponseDto> responseDtos = new ArrayList<>();
        List<LocationEntity> locationEntities = this.locationRepository
                .findOrphanLocationsByUserName(userName);
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTag("Unknown");
        tagEntity.setLocations(locationEntities);

        return  this.mappingToTagResponseDto(tagEntity);
    }

    private TagResponseDto mappingToTagResponseDto(TagEntity tagEntity) {

        TagResponseDto tagResponseDto = new TagResponseDto();
        tagResponseDto.setTag(tagEntity.getTag());
        tagResponseDto.setNumberOfLocations(tagEntity.getLocations().size());

        return tagResponseDto;
    }

}
