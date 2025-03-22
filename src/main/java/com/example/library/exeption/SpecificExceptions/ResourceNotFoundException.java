package com.example.library.exeption.SpecificExceptions;

import com.example.library.exeption.ApiBaseException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiBaseException {

    public ResourceNotFoundException(String object) {
        super(String.format(object==null?"not_found":object), HttpStatus.NOT_FOUND,object);
    }

}
