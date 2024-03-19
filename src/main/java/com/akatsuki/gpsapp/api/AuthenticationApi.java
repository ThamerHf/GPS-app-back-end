package com.akatsuki.gpsapp.api;


import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.AuthenticatedUserUpdateRequestDto;
import com.akatsuki.gpsapp.models.dto.request.LoginRequestDto;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.AuthenticatedUserResponseDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TokenResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/auth", consumes = {MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE})
public interface AuthenticationApi {

    @PostMapping(path = "/register")
    public ResponseEntity<GenericResponseDto> register(@RequestBody RegisterRequestDto request) throws Exception;

    @PostMapping(path = "/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto request) throws CustomizedException;

    @PostMapping(path = "/logout")
    public ResponseEntity<GenericResponseDto> logout() throws CustomizedException;

    @GetMapping(path = "/auhtenticatedUser")
    public ResponseEntity<AuthenticatedUserResponseDto> getAuthenticatedUser() throws CustomizedException;

    @PatchMapping(path = "/auhtenticatedUser")
    ResponseEntity<AuthenticatedUserResponseDto> updateUser(
            @RequestBody(required = true) AuthenticatedUserUpdateRequestDto authenticatedUserUpdateRequestDto)
            throws CustomizedException;

}
