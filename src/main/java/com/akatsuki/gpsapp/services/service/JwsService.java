package com.akatsuki.gpsapp.services.service;

import org.springframework.stereotype.Service;

@Service
public interface JwsService {

    public String extractUserName(String jwt);

    public boolean isTokenValid(String jwtToken);
}
