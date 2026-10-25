package com.notification_service.exception;


public class ResourceNotFoundException extends RuntimeException implements AbstractException{

    Integer statusCode;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode() {
        return 0;
    }

    @Override
    public String getErrorMessage() {
        return "";
    }
}
