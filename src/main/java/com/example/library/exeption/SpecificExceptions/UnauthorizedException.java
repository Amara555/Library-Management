package com.example.library.exeption.SpecificExceptions;

import com.example.library.exeption.ApiBaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiBaseException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
    }
}
