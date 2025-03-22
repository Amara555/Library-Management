package com.example.library.exeption;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public  class ApiBaseException extends RuntimeException {
    String message;
    HttpStatus status;
    String object;
 String errorCode;


    public ApiBaseException(String message,HttpStatus status, String object, String errorCode) {
        super(message);
        this.message = message;
        this.status = status;
        this.object = object;
        this.errorCode = errorCode;
    }

    public ApiBaseException(String message,HttpStatus status, String errorCode) {
        super(message);
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
    }

}