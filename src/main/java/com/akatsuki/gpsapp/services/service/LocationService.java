package com.akatsuki.gpsapp.services.service;

import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {

    List<LocationResponseDto> getAllLocations();
}
