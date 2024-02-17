package com.akatsuki.gpsapp.config;

import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.services.service.JwsService;
import com.akatsuki.gpsapp.services.service.UserService;
import com.akatsuki.gpsapp.services.serviceimpl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwsAuthFilterChain extends OncePerRequestFilter {

    private final JwsService jwtService;
    @Autowired
    private final UserService userService;

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

        jwtToken = authentHeader.substring("Bearer ".length());
        userName = jwtService.extractUserName(jwtToken);
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userEntity = (UserEntity) this.userService.loadUserByUsername(userName);
            if(userEntity == null) {
                filterChain.doFilter(request, response);

                return;
            }

            if(this.jwtService.isTokenValid(jwtToken,userEntity.getUsername())) {
                this.jwtService.blackListOldTokensIfExist(jwtToken, userName);
                UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
                        userEntity,
                        null,
                        userEntity.getAuthorities()
                );
                authtoken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authtoken);

            }

            filterChain.doFilter(request,response);

        }

    }
}
