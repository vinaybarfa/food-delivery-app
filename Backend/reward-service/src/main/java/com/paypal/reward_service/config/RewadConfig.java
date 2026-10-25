package com.paypal.reward_service.config;

import jdk.jfr.consumer.RecordedMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJacksonJsonMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
@EnableJpaAuditing
public class RewadConfig {

    @Bean
    public RecordMessageConverter converter() {
        return new StringJacksonJsonMessageConverter();
    }
}
