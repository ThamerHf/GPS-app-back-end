package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.AuthenticatedUserUpdateRequestDto;
import com.akatsuki.gpsapp.models.dto.request.RegisterRequestDto;
import com.akatsuki.gpsapp.models.dto.response.AuthenticatedUserResponseDto;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.models.enums.ResponseMessage;
import com.akatsuki.gpsapp.models.enums.Role;
import com.akatsuki.gpsapp.repository.UserRepository;
import com.akatsuki.gpsapp.services.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> findByUserName(String userName) {

        return this.userRepository.findByUserName(userName);
    }

    @Override
    public UserEntity getByUserName() throws CustomizedException {
        String userName = this.getAuthenticatedUser();
        Optional<UserEntity> userEntity = this.findByUserName(userName);

        return userEntity.orElse(null);
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
        userEntity.setRole(Role.USER);
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

        return userEntity.orElse(null);

    }

    @Override
    public String getAuthenticatedUser() {
        // Get the security context from the thread-local holder
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // Get the authentication object from the security context
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Get the username from the authentication object
            return authentication.getName();
        }

        // Return null if no authenticated user is found
        return null;
    }

    @Override
    public AuthenticatedUserResponseDto getAuthenticatedUserDto() throws CustomizedException {
        UserEntity user = this.getByUserName();

        return this.mappingToAuthenticatedUserResponseDto(user);
    }

    @Override
    public AuthenticatedUserResponseDto updateUser(AuthenticatedUserUpdateRequestDto authenticatedUserUpdateRequestDto)
            throws CustomizedException{

        UserEntity userEntityResponse = this.mappingRequestDtoToUserEntity(authenticatedUserUpdateRequestDto);
        userEntityResponse = this.userRepository.save(userEntityResponse);
        AuthenticatedUserResponseDto authenticatedUserResponseDto = this.mappingToAuthenticatedUserResponseDto(userEntityResponse);

        return authenticatedUserResponseDto ;
    }


    private UserEntity mappingRequestDtoToUserEntity(AuthenticatedUserUpdateRequestDto authenticatedUserUpdateRequestDto)
            throws CustomizedException{

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(authenticatedUserUpdateRequestDto.getFirstName());
        userEntity.setLastName(authenticatedUserUpdateRequestDto.getLastName());
        userEntity.setEmail(authenticatedUserUpdateRequestDto.getEmail());
        if (!(this.passwordEncoder.matches(authenticatedUserUpdateRequestDto.getOldPwd(),
                this.getByUserName().getPassword()))) {
            throw new CustomizedException(ResponseMessage.PASSWORD_WRONG.toString(),
                    HttpStatus.BAD_REQUEST);
        }

        userEntity.setPassword(this.passwordEncoder.encode(authenticatedUserUpdateRequestDto.getNewPwd()));
        return userEntity;

    }

    private AuthenticatedUserResponseDto mappingToAuthenticatedUserResponseDto(UserEntity user) {
        AuthenticatedUserResponseDto userResponseDto = new AuthenticatedUserResponseDto();
        userResponseDto.setUserName(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());

        return userResponseDto;
    }
}
