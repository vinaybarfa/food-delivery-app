package com.foodie.restaurant_service.config;

import com.foodie.restaurant_service.security.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")
public class AppConfig {

    // when we create a bean then we do not have to create a Object every time
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    // Now our JPA is also attached with a Auditor
    @Bean
    public AuditorAware<String> getAuditorAwareImpl() {
        return new AuditorAwareImpl();
    }

}
