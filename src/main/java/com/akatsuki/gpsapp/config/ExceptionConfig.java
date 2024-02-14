package com.akatsuki.gpsapp.config;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@ControllerAdvice
public class ExceptionConfig implements HandlerInterceptor {
    @ExceptionHandler(CustomizedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GenericResponseDto> handleCustomException(CustomizedException ex) {
        GenericResponseDto customExceptionResponseDto = new GenericResponseDto();
        customExceptionResponseDto.setMessage(ex.getMessage());

        return new ResponseEntity<>(customExceptionResponseDto, ex.getStatus());
    }
}
