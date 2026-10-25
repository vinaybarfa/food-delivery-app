package com.notification_service.exception;

public interface AbstractException {

    int getStatusCode();
    String getErrorMessage();

}
