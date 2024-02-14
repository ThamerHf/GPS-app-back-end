package com.akatsuki.gpsapp.repository;

import com.akatsuki.gpsapp.models.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TokenRepository extends CrudRepository<TokenEntity, UUID> {

}
