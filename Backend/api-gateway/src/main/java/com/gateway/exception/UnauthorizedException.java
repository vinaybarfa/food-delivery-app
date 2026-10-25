package com.gateway.exception;

public class UnauthorizedException
        extends BusinessException {

    public UnauthorizedException(
            String message
    ) {
        super(message);
    }
}