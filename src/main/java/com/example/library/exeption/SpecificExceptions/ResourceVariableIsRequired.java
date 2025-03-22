package com.example.library.exeption.SpecificExceptions;

import com.example.library.exeption.ApiBaseException;
import org.springframework.http.HttpStatus;

public class ResourceVariableIsRequired extends ApiBaseException {
    public ResourceVariableIsRequired(String message) {
        super(message, HttpStatus.BAD_REQUEST, "Some Parameters Is Required");
    }
}
