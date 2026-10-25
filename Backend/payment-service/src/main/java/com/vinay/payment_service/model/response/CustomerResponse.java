package com.vinay.payment_service.model.response;

public record CustomerResponse (
        Long customerId,
        String name,
        String email,
        String phone,
        String address,
        double amountAvailable
){
}
