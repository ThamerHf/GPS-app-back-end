package com.akatsuki.gpsapp.controller;

import com.akatsuki.gpsapp.api.LocationApi;
import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.models.dto.response.TagResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import com.akatsuki.gpsapp.services.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LocationController implements LocationApi {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public List<LocationResponseDto> getAllLocations() {
        List<LocationResponseDto> locations = locationService.getAllLocations();
        System.out.println(locations);
        return locations;
    }

    @Override
    public ResponseEntity<LocationResponseDto> createLocation(LocationRequestDto locationRequestDto) {
        LocationResponseDto responseDto = this.locationService.createLocation(locationRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateLocation(long id, LocationRequestDto locationRequestDto)
            throws CustomizedException {
        locationService.updateLocation(id, locationRequestDto);
        return null;
    }

    @Override
    public ResponseEntity<List<LocationResponseDto>> getTag(Long tagId)
            throws CustomizedException {

        List<LocationResponseDto> locationResponseDtos = new ArrayList<>();
        try {
            locationResponseDtos = this.locationService.getTag(tagId);
        } catch (Exception e) {
            this.handleException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(locationResponseDtos);
    }

    @Override
    public ResponseEntity<List<TagResponseDto>> getTags() throws CustomizedException {

        List<TagResponseDto> tagResponseDtos = new ArrayList<>();
        try {
            tagResponseDtos = this.locationService.getTags();
        } catch (Exception e) {
            this.handleException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDtos);
    }

    @Override
    public ResponseEntity<List<TagResponseDto>> getOwnedTags() throws CustomizedException {

        List<TagResponseDto> tagResponseDtos = new ArrayList<>();
        try {
            tagResponseDtos = this.locationService.getOwnedTags();
        } catch (Exception e) {
            this.handleException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDtos);
    }

    @Override
    public ResponseEntity<List<TagResponseDto>> getSharedTags() throws CustomizedException {

        List<TagResponseDto> tagResponseDtos = new ArrayList<>();
        try {
            tagResponseDtos = this.locationService.getSharedTags();
        } catch (Exception e) {
            this.handleException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDtos);
    }

    @Override
    public ResponseEntity<LocationResponseDto> getLocation(long id) {
        Optional<LocationEntity> locationOptional = locationService.getLocationById(id);
        return null;
    }

    @Override
    public ResponseEntity<List<LocationResponseDto>> getLocationsByTag() {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteLocation(long id) {
        return null;
    }

    private void handleException(Exception exception) throws CustomizedException {
        if(exception instanceof CustomizedException) {
            throw new CustomizedException(exception.getMessage(), ((CustomizedException) exception).getStatus());
        }

        throw new CustomizedException();
    }

}
