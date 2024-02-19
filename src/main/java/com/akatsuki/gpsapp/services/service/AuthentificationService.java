package com.akatsuki.gpsapp.services.service;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthentificationService {


    GenericResponseDto register(RegisterRequestDto request) throws CustomizedException;
}
