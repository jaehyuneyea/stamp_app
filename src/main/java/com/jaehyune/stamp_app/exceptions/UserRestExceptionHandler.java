package com.jaehyune.stamp_app.exceptions;

import com.jaehyune.stamp_app.rest.IdNotFoundErrorResponse;
import com.jaehyune.stamp_app.rest.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IdNotFoundErrorResponse> userNotFoundException(IdNotFoundException exc) {
        IdNotFoundErrorResponse error = new IdNotFoundErrorResponse();

        // build out the error response
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());


        // in all exceptions, we return the response entity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IdNotFoundErrorResponse> handleGenericException(Exception exc) {
        IdNotFoundErrorResponse error = new IdNotFoundErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
