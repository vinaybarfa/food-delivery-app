package com.vinay.payment_service.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CustomerRequest (
        @NotBlank(message = "Name can't be Blank") String name,
        @NotBlank(message = "Email cannot be Blank") @Email(message = "supplied email is not valid") String email,
        @NotBlank(message = "Customer Phone number is required") String phone,
        String address,
        @Positive(message = "Amount Available must be greater the 0") double amountAvailable
){
}
