package com.akatsuki.gpsapp.models.enums;

public enum Rights {
    ADMIN("admin"),
    EDITOR("editor"),
    VIEWER("viewer");

    private String right;

    private Rights(String right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return this.right;
    }
}
