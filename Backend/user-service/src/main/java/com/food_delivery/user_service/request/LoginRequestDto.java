package com.food_delivery.user_service.request;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
