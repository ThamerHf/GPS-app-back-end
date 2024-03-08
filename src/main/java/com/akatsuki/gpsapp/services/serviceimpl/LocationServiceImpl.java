package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.repository.LocationRepository;
import com.akatsuki.gpsapp.services.service.LocationService;
import com.akatsuki.gpsapp.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;
    UserService userService;

    public List<LocationResponseDto> getAllLocations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Collections.emptyList();
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<UserEntity> userEntity = userService.findByUserName(userDetails.getUsername());
            if(userEntity.isPresent()) {
                UserEntity userEntity1 = userEntity.get();
                List<LocationEntity> locations = userEntity1.getLocations();
                return locations.stream()
                        .map(this::mappingToLocationResponseDto)
                        .collect(Collectors.toList());
            }
            return null;

    }

    public Optional<LocationEntity> getLocationById(long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserEntity> userEntity = userService.findByUserName(userDetails.getUsername());
        if(userEntity.isPresent()) {
            UserEntity userEntity1 = userEntity.get();
            List<LocationEntity> locations = userEntity1.getLocations();
            return locations.stream().filter(location -> location.getLocationId().equals(id))
                    .findFirst();
        }else {
            return Optional.empty();
        }
    }

    public void deleteLocation(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<LocationEntity> location = locationRepository.findById(id);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserEntity> userEntity = userService.findByUserName(userDetails.getUsername());
        if (userEntity.isPresent() && location.isPresent()) {
            LocationEntity locationGot = location.get();
            UserEntity userEntity1 = userEntity.get();
            List<LocationEntity> locations = userEntity1.getLocations();
            if (locations.contains(locationGot)) {
                locationRepository.delete(locationGot);
            }
        }
    }

    private LocationResponseDto mappingToLocationResponseDto(LocationEntity locationEntity) {
            LocationResponseDto locationResponseDto = new LocationResponseDto();
            locationResponseDto.setLocationId(locationEntity.getLocationId());
            locationResponseDto.setTitle(locationEntity.getTitle());
            locationResponseDto.setCoord(locationEntity.getCoord());
            locationResponseDto.setAdresse(locationEntity.getAdresse());
            locationResponseDto.setDescription(locationEntity.getDescription());

            return locationResponseDto;
    }


}
