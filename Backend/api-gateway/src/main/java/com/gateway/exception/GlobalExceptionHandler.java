package com.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * RESOURCE NOT FOUND
     */
    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public Mono<ResponseEntity<ErrorResponse>>
    handleResourceNotFound(
            ResourceNotFoundException ex,
            ServerWebExchange exchange
    ) {

        return buildErrorResponse(
                ex,
                HttpStatus.NOT_FOUND,
                exchange
        );
    }

    /*
     * UNAUTHORIZED
     */
    @ExceptionHandler(
            UnauthorizedException.class
    )
    public Mono<ResponseEntity<ErrorResponse>>
    handleUnauthorized(
            UnauthorizedException ex,
            ServerWebExchange exchange
    ) {

        return buildErrorResponse(
                ex,
                HttpStatus.UNAUTHORIZED,
                exchange
        );
    }

    /*
     * VALIDATION
     */
    @ExceptionHandler(
            ValidationException.class
    )
    public Mono<ResponseEntity<ErrorResponse>>
    handleValidation(
            ValidationException ex,
            ServerWebExchange exchange
    ) {

        return buildErrorResponse(
                ex,
                HttpStatus.BAD_REQUEST,
                exchange
        );
    }

    /*
     * BUSINESS EXCEPTION
     */
    @ExceptionHandler(
            BusinessException.class
    )
    public Mono<ResponseEntity<ErrorResponse>>
    handleBusinessException(
            BusinessException ex,
            ServerWebExchange exchange
    ) {

        return buildErrorResponse(
                ex,
                HttpStatus.BAD_REQUEST,
                exchange
        );
    }

    /*
     * GLOBAL EXCEPTION
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>>
    handleException(
            Exception ex,
            ServerWebExchange exchange
    ) {

        return buildErrorResponse(
                ex,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exchange
        );
    }

    /*
     * COMMON RESPONSE BUILDER
     */
    private Mono<ResponseEntity<ErrorResponse>>
    buildErrorResponse(
            Exception ex,
            HttpStatus status,
            ServerWebExchange exchange
    ) {

        ErrorResponse response =
                ErrorResponse.builder()

                        .timestamp(
                                LocalDateTime.now()
                        )

                        .status(
                                status.value()
                        )

                        .error(
                                status.name()
                        )

                        .message(
                                ex.getMessage()
                        )

                        .path(
                                exchange.getRequest()
                                        .getURI()
                                        .getPath()
                        )

                        .details(
                                List.of(
                                        ex.getClass()
                                                .getSimpleName()
                                )
                        )

                        .traceId(
                                UUID.randomUUID()
                                        .toString()
                        )

                        .build();

        return Mono.just(
                ResponseEntity
                        .status(status)
                        .body(response)
        );
    }
}