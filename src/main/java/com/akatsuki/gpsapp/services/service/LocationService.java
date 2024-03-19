package com.akatsuki.gpsapp.services.service;
import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TagResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {
    public List<LocationResponseDto> getTag(Long tagId) throws CustomizedException;

    public List<LocationResponseDto> getAllLocations();

    public LocationResponseDto createLocation(LocationRequestDto locationRequestDto) throws CustomizedException;

    public LocationResponseDto updateLocation(long id, LocationRequestDto locationRequestDto) throws CustomizedException;

    public List<TagResponseDto> getTags() throws CustomizedException;

    public List<TagResponseDto> getOwnedTags() throws CustomizedException;

    public List<TagResponseDto> getSharedTags() throws CustomizedException;

    public Optional<LocationEntity> getLocationById(long id);
}
