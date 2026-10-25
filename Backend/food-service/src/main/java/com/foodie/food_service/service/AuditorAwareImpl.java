package com.foodie.food_service.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {       // this method will return the current name of the Auditor
        // steps:-
        // get exception context
        // get authentication
        // get the principle
        // get the username

        return Optional.of("Vinay Barfa");
    }
}
