package com.akatsuki.gpsapp.config;

import com.akatsuki.gpsapp.services.service.JwsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwsAuthFilterChain extends OncePerRequestFilter {

    private final JwsService jwtService;

    public JwsAuthFilterChain(JwsService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authentHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userName;
        if(authentHeader == null || !authentHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authentHeader.split(" ")[1];
        if(this.jwtService.isTokenValid(jwtToken)) {
            userName = this.jwtService.extractUserName(jwtToken);
        }

    }
}
