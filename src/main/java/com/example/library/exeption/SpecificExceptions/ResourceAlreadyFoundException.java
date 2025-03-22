package com.example.library.exeption.SpecificExceptions;

import com.example.library.exeption.ApiBaseException;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyFoundException extends ApiBaseException {

    public ResourceAlreadyFoundException(String object) {
        super(String.format("Already_found"), HttpStatus.NOT_FOUND,object);
    }

}
