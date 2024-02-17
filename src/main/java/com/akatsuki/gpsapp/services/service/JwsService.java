package com.akatsuki.gpsapp.services.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwsService {

    public String extractUserName(String jwt);

    public boolean isTokenValid(String jws, String userName);

    public void blackListOldTokensIfExist(String jws, String userName);
}
