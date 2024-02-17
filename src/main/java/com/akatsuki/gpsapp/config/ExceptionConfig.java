package com.akatsuki.gpsapp.config;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.response.GenericResponseDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.BindException;

@ControllerAdvice
public class ExceptionConfig implements HandlerInterceptor {
    @ExceptionHandler(CustomizedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GenericResponseDto> handleCustomException(CustomizedException ex) {
        GenericResponseDto customExceptionResponseDto = new GenericResponseDto();
        customExceptionResponseDto.setMessage(ex.getMessage());

        return new ResponseEntity<>(customExceptionResponseDto, ex.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericResponseDto> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Missing "
                + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericResponseDto);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericResponseDto> handleNullPointerException(
            NullPointerException ex) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Null pointer: "
                + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericResponseDto);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericResponseDto> handleBindException(BindException ex) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Erreur de liaison de paramètres de requête: "
                + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericResponseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericResponseDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Error: Cannot parse request body");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericResponseDto);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<GenericResponseDto> handleMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setMessage("Unsupported Media Type: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(genericResponseDto);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Object> handleMediaTypeNotAcceptableException(
            HttpMediaTypeNotAcceptableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body("Not Acceptable: " + ex.getMessage());
    }
}
