package com.akatsuki.gpsapp.services.service;

import com.akatsuki.gpsapp.models.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JwsService {

    public String extractUserName(String jwt);

    public boolean isTokenValid(String jws, String userName);

    public String generateAuthToken(Map<String, Object> extraClaims, UserEntity user);

    public String generateAuthToken(UserEntity user);

    public void blackListOldTokensIfExist(String jws, String userName);

    public void blackListAllToken(String userName);
}
