package com.akatsuki.gpsapp.services.service;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {

    public Optional<UserEntity> findByUserName(String userName);

    public Optional<UserEntity> getByUserName();

    public void checkIfUserExist(String userName) throws CustomizedException;

    public void createUser(RegisterRequestDto request);

    public String getAuthenticatedUser();

    public String updateUser(RegisterRequestDto request);

}
