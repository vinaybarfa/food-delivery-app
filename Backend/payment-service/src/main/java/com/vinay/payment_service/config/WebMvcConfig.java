package com.vinay.payment_service.config;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApplicationProperties properties;

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        ApplicationProperties.Cors cors = properties.getCors();
        registry.addMapping(cors.getPathPattern())
                .allowCredentials(cors.isAllowCredentials())
                .allowedHeaders(cors.getAllowedHeader())
                .allowedMethods(cors.getAllowsMethods())
                .allowedOriginPatterns(cors.getAllowedOriginPatterns())
                .maxAge(3600);  // Cache preflight response for 1 hour
    }
}
