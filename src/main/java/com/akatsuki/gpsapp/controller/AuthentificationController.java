package com.akatsuki.gpsapp.controller;

import com.akatsuki.gpsapp.api.AuthentificationApi;
import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LoginRequestDto;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TokenResponseDto;
import com.akatsuki.gpsapp.services.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthentificationController implements AuthentificationApi {
    private final AuthentificationService authentificationService;


    public AuthentificationController(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @Override
    public ResponseEntity<GenericResponseDto> register(RegisterRequestDto request) throws CustomizedException {
        GenericResponseDto response = null;
        try {
            response = this.authentificationService.register(request);
        } catch (Exception e) {
            this.handleException(e);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TokenResponseDto> login(LoginRequestDto request) {
        return null;
    }

    @Override
    public ResponseEntity<String> login() {
        return null;
    }
    private void handleException(Exception exception) throws CustomizedException {
        if(exception instanceof CustomizedException) {
            throw new CustomizedException(exception.getMessage(), ((CustomizedException) exception).getStatus());
        }

        throw new CustomizedException();
    }
}
