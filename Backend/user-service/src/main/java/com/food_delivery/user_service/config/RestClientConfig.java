package com.food_delivery.user_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${userService.base.url}")
    private String BASE_URL;

    @Bean
    @Qualifier("userRestClient")
    public RestClient getUserServiceRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
}
