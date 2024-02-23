package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.repository.LocationRepository;
import com.akatsuki.gpsapp.services.service.LocationService;
import com.akatsuki.gpsapp.services.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.desktop.SystemEventListener;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserService userService;

    public List<LocationResponseDto> getAllLocations() {
        String username = this.userService.getAuthenticatedUser();
        Optional<UserEntity> userEntity = userService.findByUserName(username);
        if(userEntity.isPresent()) {
                UserEntity userEntity1 = userEntity.get();
                List<LocationEntity> locations = userEntity1.getLocations();
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
    public LocationEntity createLocation(LocationRequestDto locationRequestDto) {
        LocationEntity locationEntity = mappingToLocationEntity(locationRequestDto);
        String username = this.userService.getAuthenticatedUser();
        Optional<UserEntity> userEntity = userService.findByUserName(username);
        if(userEntity.isPresent()) {
            UserEntity userEntityToSave = userEntity.get();
            locationEntity.setUser(userEntityToSave);
            System.out.println(locationEntity.getUser().getFirstName());

        }
        return this.locationRepository.save(locationEntity);
    }

    @Override
    public void updateLocation(long id, LocationRequestDto locationRequestDto) throws CustomizedException {
        Optional<LocationEntity> locationEntityBddOpt = locationRepository.findById(id);
        LocationEntity locationEntityRequest = mappingToLocationEntity(locationRequestDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserEntity> userEntity = userService.findByUserName(userDetails.getUsername());
        if(locationEntityBddOpt.isPresent()) {
            LocationEntity locationEntityBdd = locationEntityBddOpt.get();
            if (userEntity.isPresent()) {
                UserEntity userEntityToSave = userEntity.get();
                List<LocationEntity> locations = userEntityToSave.getLocations();
                locations.stream()
                        .filter(item -> item.getLocationId() == locationEntityBdd.getLocationId())
                        .findFirst()
                        .ifPresent(item -> item = locationEntityBdd);
            }
            locationRepository.save(locationEntityBdd);

        } else {
            throw new CustomizedException(ResponseMessage.LOCATION_NOT_FOUND.toString()
                    , HttpStatus.NOT_FOUND);
        }
    }

    private LocationEntity mappingToLocationEntity(LocationRequestDto locationRequestDto) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationId(locationRequestDto.getLocationId());
        locationEntity.setTitle(locationRequestDto.getTitle());
        locationEntity.setCoord(locationRequestDto.getCoord());
        locationEntity.setAdresse(locationRequestDto.getAdresse());
        locationEntity.setDescription(locationRequestDto.getDescription());
        locationEntity.setImages(locationRequestDto.getImages());
        locationEntity.setTags(locationRequestDto.getTags());
        locationEntity.setTags(locationRequestDto.getTags());

        return locationEntity;

    }

    private LocationResponseDto mappingToLocationResponseDto(LocationEntity locationEntity) {
        LocationResponseDto locationResponseDto = new LocationResponseDto();
        locationResponseDto.setLocationId(locationEntity.getLocationId());
        locationResponseDto.setTitle(locationEntity.getTitle());
        locationResponseDto.setCoord(locationEntity.getCoord());
        locationResponseDto.setAdresse(locationEntity.getAdresse());
        locationResponseDto.setDescription(locationEntity.getDescription());
        locationResponseDto.setImages(locationEntity.getImages());
        locationResponseDto.setTags(locationEntity.getTags());

        return locationResponseDto;
    }



}
