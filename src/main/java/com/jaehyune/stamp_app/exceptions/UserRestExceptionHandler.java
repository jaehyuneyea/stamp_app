package com.jaehyune.stamp_app.exceptions;

import com.jaehyune.stamp_app.rest.error.IdNotFoundErrorResponse;
import com.jaehyune.stamp_app.rest.error.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A Controller Advice for exceptions. Meaning all Exceptions will look here to be handled.
 */
@ControllerAdvice
public class UserRestExceptionHandler {

    /**
     * An exception handler for when a user's id has not been found.
     * @param exc catches the IdNotFoundException
     * @return an error response with HTTP Status for NOT FOUND
     */
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

    /**
     * A generic exception handler.
     * @param exc catches any subset of Exceptions.
     * @return an error response with HTTP Status for BAD REQUEST
     */
    @ExceptionHandler
    public ResponseEntity<IdNotFoundErrorResponse> handleGenericException(Exception exc) {
        IdNotFoundErrorResponse error = new IdNotFoundErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
