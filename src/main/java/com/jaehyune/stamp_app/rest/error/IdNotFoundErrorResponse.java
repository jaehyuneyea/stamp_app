package com.jaehyune.stamp_app.rest.error;

/**
 * An error response for when an id is not found.
 * Builds the error message with its status code, error message and timestamp of the error occurred.
 */
public class IdNotFoundErrorResponse {

    private int status;
    private String message;
    private long timestamp;

    public IdNotFoundErrorResponse() {

    }

    /**
     *
     * @param status status code of the error
     * @param message message of the error
     * @param timestamp the time when the error occurred
     */
    public IdNotFoundErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
