package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.repository.UserRepository;
import com.akatsuki.gpsapp.services.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public Optional<UserEntity> findByUserName(String userName) {
        return this.userRepository.findByUserName((userName));
    }

    public void checkIfUserExist(String userName) throws CustomizedException {
        Optional<UserEntity> userEntity = this.findByUserName(userName);
        if(userEntity.isPresent()) {
            throw new CustomizedException(ResponseMessage.USER_NAME_ALREADY_EXISTS.toString()
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void createUser(RegisterRequestDto request) {
        UserEntity userEntity = this.mappingToUserEntity(request);
        this.userRepository.save(userEntity);
    }

    private UserEntity mappingToUserEntity(RegisterRequestDto request) {
        return UserEntity.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(new BCryptPasswordEncoder().encode(request.getPwd()))
                .build();
    }
}
