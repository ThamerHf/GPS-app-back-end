package com.akatsuki.gpsapp.api;


import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LoginRequestDto;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TokenResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/auth", consumes = {MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE})
public interface AuthentificationApi {

    @PostMapping(path = "/register")
    public ResponseEntity<GenericResponseDto> register(@RequestBody RegisterRequestDto request) throws Exception;

    @PostMapping(path = "/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto request);

    @PostMapping(path = "/logout")
    public ResponseEntity<String> login();

}
