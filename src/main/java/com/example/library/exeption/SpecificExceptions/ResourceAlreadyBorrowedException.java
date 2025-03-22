package com.example.library.exeption.SpecificExceptions;

import com.example.library.exeption.ApiBaseException;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyBorrowedException extends ApiBaseException {

    public ResourceAlreadyBorrowedException(String object) {
        super(String.format("This Book Already Borrowed"), HttpStatus.BAD_REQUEST,object);
    }

}
