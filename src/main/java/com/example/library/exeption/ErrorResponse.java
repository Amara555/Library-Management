package com.example.library.exeption;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final HttpStatus status;
    private final String error;
    private final String message;
    private final String errorCode;
    private final String path;

    public ErrorResponse( HttpStatus status, String error, String message, String errorCode, String path) {

        this.status = status;
        this.error = error;
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
    }



    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getPath() {
        return path;
    }
}
