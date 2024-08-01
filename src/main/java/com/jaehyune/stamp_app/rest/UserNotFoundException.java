package com.jaehyune.stamp_app.rest;

public class UserNotFoundException extends RuntimeException {

    // exception gets thrown with a specific error response to return
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
