package com.example.library.security.model;

public enum LoginErrorsConstants {

    USER_PASSWORD_SYSTEM_NAME_REQUIRED("USER_PASSWORD_SYSTEM_NAME_REQUIRED"),
    USER_OR_PASSWORD_WRONG("USER_OR_PASSWORD_WRONG"),
    USER_HAS_NO_DEPARTMENTS("USER_HAS_NO_DEPARTMENTS"),
    USER_HAS_NO_PRIVILEGES("USER_HAS_NO_PRIVILEGES");

    private String value;

    LoginErrorsConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
