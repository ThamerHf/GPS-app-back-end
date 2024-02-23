package com.akatsuki.gpsapp.api;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/location", consumes = {MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE})
public interface LocationApi {

    @GetMapping("/")
    ResponseEntity<List<LocationResponseDto>> getAllLocations();


    @GetMapping("/{id} ")
    ResponseEntity<LocationResponseDto> getLocation(@PathVariable long id);

    @PostMapping("/")
    ResponseEntity<String> createLocation(LocationRequestDto locationRequestDto);

    @PutMapping("/{id}")
    ResponseEntity<String> updateLocation(@PathVariable long id, @RequestBody LocationRequestDto locationRequestDto) throws CustomizedException;


}
