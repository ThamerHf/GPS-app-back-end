package com.akatsuki.gpsapp.services.service;

import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {

    List<LocationResponseDto> getAllLocations();
    Optional<LocationEntity> getLocationById(long id);

}
