package com.akatsuki.gpsapp.controller;

import com.akatsuki.gpsapp.api.LocationApi;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.services.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<LocationResponseDto> getLocation() {
        return null;
    }

    @Override
    public ResponseEntity<List<LocationResponseDto>> getLocationsByTag() {
        return null;
    }

    @Override
    public ResponseEntity<LocationResponseDto> createLocation() {
        return null;
    }

    @Override
    public ResponseEntity<LocationResponseDto> createLocations() {
        return null;
    }


}
