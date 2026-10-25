package com.vinay.payment_service.exception;

import com.vinay.payment_service.utils.LogSanitizer;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @ExceptionHandler(CustomerNotFoundException.class)
    private ResponseEntity<@NotNull ProblemDetail> handleCustomerNotFound(
            CustomerNotFoundException ex, WebRequest request
    ) {
        log.warn("Customer not found: {}", LogSanitizer.sanitizerException(ex));

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND, LogSanitizer.sanitizerException(ex)
                );
        problemDetail.setTitle("Customer Not Found");
        problemDetail.setType(URI.create("https://api.microservices.com/error/not-found"));
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());

        addCorrelationId(problemDetail, request);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull ProblemDetail> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest webRequest) {
        log.warn("Validation failed: {}", LogSanitizer.sanitizerException(ex));

        List<ApiValidationError> validationErrors =
                ex.getAllErrors().stream()
                        .map(
                                objectError -> {
                                    FieldError fieldError = (FieldError) objectError;
                                    return new ApiValidationError(
                                            fieldError.getObjectName(),
                                            fieldError.getField(),
                                            fieldError.getRejectedValue(),
                                            Objects.requireNonNull(fieldError.getDefaultMessage())
                                    );
                                }
                        ).sorted(Comparator.comparing(ApiValidationError::field))
                        .toList();

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST, "Invalid request content."
                );
        problemDetail.setTitle("Constraint Violation");
        problemDetail.setType(URI.create("https://api.microservices.com/errors/validation-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("violations", validationErrors);
        addCorrelationId(problemDetail, webRequest);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<@NonNull ProblemDetail> handleConstraintsViolation(
            CustomerNotFoundException ex, WebRequest webRequest
    ) {
        log.warn("Constraint violation: {}", LogSanitizer.sanitizerException(ex));
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, LogSanitizer.sanitizerException(ex)
        );
        problemDetail.setTitle("Constraint Violation");
        problemDetail.setType(URI.create("https://api.microservice.com/errors/validation-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        addCorrelationId(problemDetail, webRequest);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<@NonNull ProblemDetail> handleIllegalArgument(
            IllegalArgumentException ex, WebRequest webRequest
    ) {
        log.warn("Illegal argument: {}", LogSanitizer.sanitizerException(ex));
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, LogSanitizer.sanitizerException(ex)
        );
        problemDetail.setTitle("Invalid Request");
        problemDetail.setType(URI.create("https://api.microservices.com/errors/invalid-request"));
        problemDetail.setProperty("timestamp", Instant.now());
        addCorrelationId(problemDetail, webRequest);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, WebRequest webRequest) {
        log.error("Unexpected error occurred: ", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"
        );

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("timestamp", Instant.now());
        addCorrelationId(problemDetail, webRequest);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    public void addCorrelationId(ProblemDetail problemDetail, WebRequest request) {

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId != null) {
            problemDetail.setProperty("correlationId", correlationId);
        }
    }

    record ApiValidationError(String object, String field, Object rejectedValue, String message) {
    }
}
