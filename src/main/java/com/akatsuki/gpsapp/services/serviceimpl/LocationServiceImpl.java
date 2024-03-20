package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TagResponseDto;
import com.akatsuki.gpsapp.models.entity.ImageEntity;
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

import java.awt.geom.Point2D;
import java.util.*;
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
    public LocationResponseDto createLocation(LocationRequestDto locationRequestDto)
            throws CustomizedException {
        LocationEntity locationEntity = mappingToLocationEntity(locationRequestDto);
        String username = this.userService.getAuthenticatedUser();
        Optional<UserEntity> userEntity = userService.findByUserName(username);
        if(userEntity.isPresent()) {
            UserEntity userEntityToSave = userEntity.get();
            locationEntity.setUser(userEntityToSave);

        }

        return this.mappingToLocationResponseDto(this.locationRepository
                .save(locationEntity));
    }

    @Override
    public LocationResponseDto updateLocation(long id, LocationRequestDto locationRequestDto) throws CustomizedException {
        Optional<LocationEntity> locationEntityBddOpt = locationRepository.findById(String.valueOf(id));
        LocationEntity locationEntityRequest = mappingToLocationEntity(locationRequestDto);
        String username = this.userService.getAuthenticatedUser();
        LocationResponseDto locationResponseDto = null;
        Optional<UserEntity> userEntity = userService.findByUserName(username);
        if(locationEntityBddOpt.isPresent()) {
            locationEntityRequest.setLocationId(locationEntityBddOpt.get().getLocationId());
            locationResponseDto = this.mappingToLocationResponseDto(
                    locationRepository.save(locationEntityRequest));

        } else {
            throw new CustomizedException(ResponseMessage.LOCATION_NOT_FOUND.toString()
                    , HttpStatus.NOT_FOUND);
        }

        return locationResponseDto;
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

    private LocationEntity mappingToLocationEntity(LocationRequestDto locationRequestDto) throws CustomizedException {
        LocationEntity locationEntity = new LocationEntity();
        if (locationRequestDto.getLocationId() != null) {
            locationEntity.setLocationId(locationRequestDto.getLocationId());
        }

        if (locationRequestDto.getTitle() != null) {
            locationEntity.setTitle(locationRequestDto.getTitle());
        }


        if (locationRequestDto.getCoord() != null &&
                !locationRequestDto.getCoord().isEmpty()) {
            if (locationRequestDto.getCoord().size()!=2) {
                throw new CustomizedException(ResponseMessage
                        .LONCATION_COORDONNE_ERROR.toString(), HttpStatus.BAD_REQUEST);
            }
            locationEntity.setCoord(new Point2D.Double(locationRequestDto.getCoord().get(0),
                    locationRequestDto.getCoord().get(1)));
        }

        if (locationRequestDto.getAdresse() != null) {
            locationEntity.setAdresse(locationRequestDto.getAdresse());
        }

        if (locationRequestDto.getDescription() != null) {
            locationEntity.setDescription(locationRequestDto.getDescription());
        }

        ImageEntity image = new ImageEntity();
        if (locationRequestDto.getImage() != null) {
            image.setFile(locationRequestDto.getImage());
            locationEntity.setImage(image);
        }

        if (!locationRequestDto.getTags().isEmpty()) {
            locationEntity.setTags(this.mapToTagEntity(locationRequestDto.getTags()));
        }

        return locationEntity;
    }

    private List<TagEntity> mapToTagEntity(List<String> tags)
            throws CustomizedException {
        List<String> ownedTags = this.getOwnedTags().stream()
                .map(TagResponseDto::getTag)
                .collect(Collectors.toList());
        tags.add("All");
        tags = new ArrayList<>(new HashSet<>(tags));
        List<TagEntity> returnedTags = new ArrayList<>();
        for (String t : tags) {
            if (!ownedTags.contains(t)) {
                returnedTags.add(this.tagRepository.save(new TagEntity(t)));
            } else {
                Optional<TagEntity> tag = this.tagRepository.findByTag(t);
                if (tag.isPresent()) {
                    returnedTags.add(this.tagRepository.findByTag(t).get());
                }
            }
        }

        return returnedTags;
    }

    private LocationResponseDto mappingToLocationResponseDto(LocationEntity locationEntity) {
        LocationResponseDto locationResponseDto = new LocationResponseDto();
        locationResponseDto.setLocationId(locationEntity.getLocationId());
        locationResponseDto.setTitle(locationEntity.getTitle());
        List<Double> coord = new ArrayList<>();
        if (locationEntity.getCoord() != null) {
            coord.add(locationEntity.getCoord().getX());
            coord.add(locationEntity.getCoord().getY());
            locationResponseDto.setCoord(coord);
        }

        locationResponseDto.setAdresse(locationEntity.getAdresse());
        locationResponseDto.setDescription(locationEntity.getDescription());
        if (locationEntity.getImage() != null) {
            locationResponseDto.setImage(locationEntity.getImage().getFile());
        } else {
            locationResponseDto.setImage(new byte[0]);
        }

        locationResponseDto.setTags(locationEntity
                .getTags().stream()
                .map(TagEntity::getTag).collect(Collectors.toList()));

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
        List<LocationEntity> locationEntities = this.locationRepository
                .findByUser(this.userService.getByUserName());
        List<TagEntity> tagEntities = new ArrayList<>();
        for (LocationEntity loc : locationEntities) {
            tagEntities.addAll(loc.getTags());
        }

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
