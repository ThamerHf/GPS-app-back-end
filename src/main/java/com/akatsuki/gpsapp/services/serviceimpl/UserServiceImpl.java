package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.repository.UserRepository;
import com.akatsuki.gpsapp.services.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
                .password(this.passwordEncoder.encode(request.getPwd()))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = this.findByUserName(username);
        if(userEntity.isPresent()) {
            return userEntity.get();
        }

        return null;
    }
}
