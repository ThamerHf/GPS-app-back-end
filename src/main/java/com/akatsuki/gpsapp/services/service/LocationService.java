package com.akatsuki.gpsapp.services.service;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {

    List<LocationResponseDto> getAllLocations();

    LocationEntity createLocation(LocationRequestDto locationRequestDto);

    void updateLocation(long id, LocationRequestDto locationRequestDto) throws CustomizedException;
}
