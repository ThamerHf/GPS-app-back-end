package com.akatsuki.gpsapp.models.enums;

public enum ResponseMessage {
    ERROR_IMAGE_LOCATION_SAVE("Error when trying to save location image"),

    TAG_NOT_FOUND("Tag not found"),
    LOGOUT_SUCCESSEFULLY("Lougout successfully"),
    LOGIN_FAIL("UserName or Password is wrong"),
    INTERNAL_ERROR_MESSAGE("Server Internal Error"),
    USER_NAME_ALREADY_EXISTS("Username already exists"),
    LOCATION_NOT_FOUND("Location not found"),
    USER_CREATED("User created successfully"),
    LOCATION_CREATED("Location created successfully");
    private String message;

    private ResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
