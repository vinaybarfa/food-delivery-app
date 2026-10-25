package com.vinay.auth_service.exception;

public class AuthenticationFailedException
        extends RuntimeException {

    public AuthenticationFailedException(
            String message
    ) {
        super(message);
    }
}