package com.example.library.exeption;

import com.example.library.exeption.SpecificExceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        HttpStatus status = determineHttpStatus(ex);
        String errorCode = determineErrorCode(ex);

        ErrorResponse errorResponse = new ErrorResponse(
                status,
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                errorCode,
                request.getDescription(false)
        );
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus determineHttpStatus(Exception ex) {
        if (ex instanceof EntityNotFoundException || ex instanceof NoSuchElementException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof NonUniqueResultException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof IllegalArgumentException || ex instanceof MethodArgumentNotValidException ||  ex instanceof ResourceVariableIsRequired) {
            return HttpStatus.BAD_REQUEST;
        }else if (ex instanceof ResourceAlreadyBorrowedException || ex instanceof ResourceAlreadyFreeException ){
            return HttpStatus.BAD_REQUEST;
        }
        else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private String determineErrorCode(Exception ex) {
        if (ex instanceof EntityNotFoundException) {
            return "RESOURCE_NOT_FOUND";
        } else if (ex instanceof NonUniqueResultException) {
            return "DUPLICATE_RECORD";
        } else if (ex instanceof IllegalArgumentException) {
            return "INVALID_ARGUMENT";
        } else {
            return "INTERNAL_ERROR";
        }
    }
}
