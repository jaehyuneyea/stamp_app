package com.jaehyune.stamp_app.rest.error;

public class IdNotFoundException extends RuntimeException {

    // exception gets thrown with a specific error response to return
    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNotFoundException(Throwable cause) {
        super(cause);
    }
}
