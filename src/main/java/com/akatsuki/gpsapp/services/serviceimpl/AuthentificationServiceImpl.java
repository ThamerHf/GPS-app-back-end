package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LoginRequestDto;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.dto.response.TokenResponseDto;
import com.akatsuki.gpsapp.models.entity.TokenEntity;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.services.service.AuthentificationService;
import com.akatsuki.gpsapp.services.service.JwsService;
import com.akatsuki.gpsapp.services.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public class AuthentificationServiceImpl implements AuthentificationService {

    private final UserService userService;

    private final JwsService jwsService;

    private final AuthenticationManager authenticationManager;

    public AuthentificationServiceImpl(UserService userService, JwsService jwsService,
                                       AuthenticationManager authenticationManager) {

        this.userService = userService;
        this.jwsService = jwsService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public GenericResponseDto register(RegisterRequestDto request) throws CustomizedException {
        this.userService.checkIfUserExist(request.getUserName());
        this.userService.createUser(request);

        return new GenericResponseDto(ResponseMessage.USER_CREATED.toString());
    }

    @Override
    public TokenResponseDto login(LoginRequestDto request) throws CustomizedException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPwd()
                    )
            );
        } catch (AuthenticationException exception) {
            throw new CustomizedException(ResponseMessage.LOGIN_FAIL.toString(), HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = this.userService.findByUserName(request.getUserName()).get();
        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        String jws = this.jwsService.generateAuthToken(userEntity);
        tokenResponseDto.setToken(jws);
        this.jwsService.blackListOldTokensIfExist(jws, userEntity.getUsername());

        return tokenResponseDto;
    }

    @Override
    public GenericResponseDto logout() {
        this.jwsService.blackListAllToken(this.userService.getAuthenticatedUser());
        GenericResponseDto response = new GenericResponseDto();
        response.setMessage(ResponseMessage.LOGOUT_SUCCESSEFULLY.toString());

        return response;
    }


}
