package com.example.library.exeption.SpecificExceptions;

import com.example.library.exeption.ApiBaseException;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyFreeException extends ApiBaseException {

    public ResourceAlreadyFreeException(String object) {
        super(String.format("This Book Is Not Borrowed"), HttpStatus.BAD_REQUEST,object);
    }

}
