package com.example.library.exeption.SpecificExceptions;

import com.example.library.exeption.ApiBaseException;
import org.springframework.http.HttpStatus;

public class NonUniqueResultException extends ApiBaseException {
    public NonUniqueResultException(String object ) {
        super(object, HttpStatus.CONFLICT,object);
    }
}
