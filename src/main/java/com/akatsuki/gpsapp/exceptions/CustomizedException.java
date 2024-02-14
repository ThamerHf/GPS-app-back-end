package com.akatsuki.gpsapp.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomizedException extends Exception {

    private String message = "InternalError";

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public CustomizedException() {
    }

    public CustomizedException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
