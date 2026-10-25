package com.gateway.exception;

public class ResourceNotFoundException
        extends BusinessException {

    public ResourceNotFoundException(
            String message
    ) {
        super(message);
    }
}