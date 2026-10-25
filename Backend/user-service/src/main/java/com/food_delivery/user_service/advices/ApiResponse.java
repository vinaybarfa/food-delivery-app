package com.food_delivery.user_service.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {       // this will help in providing better output error

    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }
    public ApiResponse(T data) {
        this();         // Here i can default constructor
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
