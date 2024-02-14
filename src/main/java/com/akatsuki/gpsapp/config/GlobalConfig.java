package com.akatsuki.gpsapp.config;

import com.akatsuki.gpsapp.repository.UserRepository;
import com.akatsuki.gpsapp.services.service.AuthentificationService;
import com.akatsuki.gpsapp.services.service.JwsService;
import com.akatsuki.gpsapp.services.service.UserService;
import com.akatsuki.gpsapp.services.serviceimpl.AuthentificationServiceImpl;
import com.akatsuki.gpsapp.services.serviceimpl.JwsServiceImpl;
import com.akatsuki.gpsapp.services.serviceimpl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GlobalConfig {

    @Bean
    public AuthentificationService authentificationService(UserService userService) {
        return new AuthentificationServiceImpl(userService);
    };
    @Bean
    public UserService userService(UserRepository repository) {
        return new UserServiceImpl(repository);
    };
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwsService jwtService() {
        return new JwsServiceImpl();
    }

}
