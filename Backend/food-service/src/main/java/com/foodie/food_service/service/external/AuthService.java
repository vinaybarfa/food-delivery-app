package com.foodie.food_service.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthService {

    @GetMapping("/auth/validate")
    Boolean validateToken(@RequestHeader("Authorization") String token);
}