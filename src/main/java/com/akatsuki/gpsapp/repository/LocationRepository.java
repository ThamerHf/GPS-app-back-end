package com.akatsuki.gpsapp.repository;

import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import com.akatsuki.gpsapp.models.entity.LocationEntity;
import com.akatsuki.gpsapp.models.entity.TokenEntity;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

    public List<LocationEntity> findByUser(UserEntity user);
}
