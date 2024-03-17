package com.akatsuki.gpsapp.controller;

import com.akatsuki.gpsapp.api.AuthenticationApi;
import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LoginRequestDto;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.AuthenticatedUserResponseDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TokenResponseDto;
import com.akatsuki.gpsapp.services.service.AuthentificationService;
import com.akatsuki.gpsapp.services.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController implements AuthenticationApi {
    private final AuthentificationService authentificationService;

    private final UserService userService;


    public AuthenticationController(AuthentificationService authentificationService, UserService userService) {
        this.authentificationService = authentificationService;
        this.userService = userService;
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
    public ResponseEntity<TokenResponseDto> login(LoginRequestDto request) throws CustomizedException {
        TokenResponseDto response = null;
        try {
            response = this.authentificationService.login(request);
        } catch (Exception e) {
            this.handleException(e);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenericResponseDto> logout() throws CustomizedException {
        GenericResponseDto response = null;
        try {
            response = this.authentificationService.logout();
        } catch (Exception e) {
            this.handleException(e);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthenticatedUserResponseDto> getAuthenticatedUser() throws CustomizedException {
        AuthenticatedUserResponseDto responseDto = null;

        try {
            responseDto = this.userService.getAuthenticatedUserDto();
        } catch (Exception e) {
            this.handleException(e);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private void handleException(Exception exception) throws CustomizedException {
        if(exception instanceof CustomizedException) {
            throw new CustomizedException(exception.getMessage(), ((CustomizedException) exception).getStatus());
        }

        throw new CustomizedException();
    }
}
