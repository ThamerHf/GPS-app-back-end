package com.akatsuki.gpsapp.models.enums;

public enum ResponseMessage {
    USER_NAME_ALREADY_EXISTS("Username already exists"),
    USER_CREATED("User created successfully");
    private String message;

    private ResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
