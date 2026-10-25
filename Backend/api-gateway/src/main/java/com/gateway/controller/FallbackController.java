package com.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/food-service")
    public Mono<ResponseEntity<Map<String, Object>>>
    foodServiceFallback() {

        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(
                                Map.of(
                                        "service", "FOOD-SERVICE",
                                        "message",
                                        "Food service is temporarily unavailable",
                                        "timestamp",
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @GetMapping("/restaurant-service")
    public Mono<ResponseEntity<Map<String, Object>>>
    restaurantServiceFallback() {

        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(
                                Map.of(
                                        "service", "RESTAURANT-SERVICE",
                                        "message",
                                        "Restaurant service is temporarily unavailable",
                                        "timestamp",
                                        LocalDateTime.now()
                                )
                        )
        );
    }
}