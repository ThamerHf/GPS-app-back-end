package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.services.service.AuthentificationService;
import com.akatsuki.gpsapp.services.service.UserService;

public class AuthentificationServiceImpl implements AuthentificationService {

    private final UserService userService;

    public AuthentificationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public GenericResponseDto register(RegisterRequestDto request) throws CustomizedException {
        this.userService.checkIfUserExist(request.getUserName());
        this.userService.createUser(request);

        return new GenericResponseDto(ResponseMessage.USER_CREATED.toString());
    }


}
