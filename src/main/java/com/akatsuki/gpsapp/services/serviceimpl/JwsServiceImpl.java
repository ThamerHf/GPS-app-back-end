package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.services.service.JwsService;
import io.jsonwebtoken.*;

public class JwsServiceImpl implements JwsService {

    @Override
    public String extractUserName(String jwt) {
        return null;
    }

    @Override
    public boolean isTokenValid(String jwtToken) {
        return false;
    }

    private Claims extractClaims(String jwt) {
        return null;
    }
}
