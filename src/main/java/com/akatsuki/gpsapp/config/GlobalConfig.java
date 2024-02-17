package com.akatsuki.gpsapp.config;

import com.akatsuki.gpsapp.repository.JwsTokenRepository;
import com.akatsuki.gpsapp.repository.UserRepository;
import com.akatsuki.gpsapp.services.service.AuthentificationService;
import com.akatsuki.gpsapp.services.service.JwsService;
import com.akatsuki.gpsapp.services.service.UserService;
import com.akatsuki.gpsapp.services.serviceimpl.AuthentificationServiceImpl;
import com.akatsuki.gpsapp.services.serviceimpl.JwsServiceImpl;
import com.akatsuki.gpsapp.services.serviceimpl.UserServiceImpl;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GlobalConfig {

    @Bean
    public AuthentificationService authentificationService(UserService userService) {
        return new AuthentificationServiceImpl(userService);
    };
    @Bean
    public UserService userService(UserRepository repository, PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(repository, passwordEncoder);
    };
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwsService jwtService(JwsTokenRepository jwsTokenRepository) {
        return new JwsServiceImpl(jwsTokenRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService(userRepository, passwordEncoder));
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

}
