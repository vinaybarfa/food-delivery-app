package com.notification_service.exception.handler;

import com.notification_service.exception.AbstractException;
import com.notification_service.exception.ResourceNotFoundException;
import com.notification_service.exception.ValidationException;
import com.notification_service.utils.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.Supplier;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException exception) {
        return genericExceptionHandler(
                exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage())
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception) {

        return genericExceptionHandler(
                exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage())
        );

    }

    public ResponseEntity<String> genericExceptionHandler(final AbstractException exception,
                                                          final Supplier<ResponseEntity<String>> runner) {
        if (CommonUtils.isNotEmpty(exception.getStatusCode())) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getErrorMessage());
        }
        return runner.get();
    }
}
