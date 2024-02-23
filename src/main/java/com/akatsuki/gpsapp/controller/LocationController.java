package com.akatsuki.gpsapp.controller;

import com.akatsuki.gpsapp.api.LocationApi;
import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.services.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LocationController implements LocationApi {

    @Autowired
    private final LocationService locationService;

    @Override
    public ResponseEntity<List<LocationResponseDto>> getAllLocations() {
        List<LocationResponseDto> locations = locationService.getAllLocations();
        return null;
    }

    @Override
    public ResponseEntity<LocationResponseDto> getLocation(long id) {
        return null;
    }

    @Override
    public ResponseEntity<String> createLocation(LocationRequestDto locationRequestDto) {
                this.locationService.createLocation(locationRequestDto);
                return new ResponseEntity<>(ResponseMessage.LOCATION_CREATED.toString(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateLocation(long id, LocationRequestDto locationRequestDto) throws CustomizedException {
        locationService.updateLocation(id,locationRequestDto);
        return null;
    }


}
