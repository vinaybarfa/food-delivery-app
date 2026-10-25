package com.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final RateLimitConfig rateLimitConfig;

    @Bean
    public RouteLocator routeLocator(
            RouteLocatorBuilder builder
    ) {

        return builder.routes()

                /*
                 * FOOD SERVICE
                 */
                .route(
                        "food-service",

                        route -> route

                                .path("/foods/**")

                                .filters(filter -> filter

                                        .rewritePath(
                                                "/foods/?(?<remaining>.*)",
                                                "/${remaining}"
                                        )

                                        .circuitBreaker(
                                                cb -> cb
                                                        .setName(
                                                                "foodCircuitBreaker"
                                                        )

                                                        .setFallbackUri(
                                                                "forward:/fallback/food-service"
                                                        )
                                        )

                                        .retry(
                                                retry -> retry

                                                        .setRetries(3)

                                                        .setMethods(
                                                                HttpMethod.GET
                                                        )

                                                        .setBackoff(
                                                                Duration.ofMillis(100),
                                                                Duration.ofMillis(1000),
                                                                2,
                                                                true
                                                        )
                                        )
                                )

                                .uri("lb://FOOD-SERVICE")
                )

                /*
                 * RESTAURANT SERVICE
                 */
                .route(
                        "restaurant-service",

                        route -> route

                                .path("/restaurants/**")

                                .filters(filter -> filter

                                        .rewritePath(
                                                "/restaurants/?(?<remaining>.*)",
                                                "/${remaining}"
                                        )

                                        .circuitBreaker(
                                                cb -> cb
                                                        .setName(
                                                                "restaurantCircuitBreaker"
                                                        )

                                                        .setFallbackUri(
                                                                "forward:/fallback/restaurant-service"
                                                        )
                                        )

                                        .requestRateLimiter(
                                                rateLimiter -> rateLimiter

                                                        .setRateLimiter(
                                                                rateLimitConfig
                                                                        .redisRateLimiter()
                                                        )

                                                        .setKeyResolver(
                                                                rateLimitConfig
                                                                        .userKeyResolver()
                                                        )
                                        )
                                )

                                .uri("lb://RESTAURANT-SERVICE")
                )

                .build();
    }
}