package com.vinay.category_service.service.external;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthService {
}
