package com.akatsuki.gpsapp.api;

import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/location", consumes = {MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE})
public interface LocationApi {

    @GetMapping("/")
    ResponseEntity<List<LocationResponseDto>> getAllLocations();


    @GetMapping("/{id}")
    ResponseEntity<LocationResponseDto> getLocation();

    @GetMapping("/{tag}")
    ResponseEntity<List<LocationResponseDto>> getLocationsByTag();

    @PostMapping("/{id}")
    ResponseEntity<LocationResponseDto> createLocation();

    @PostMapping("/")
    ResponseEntity<LocationResponseDto> createLocations();
}
