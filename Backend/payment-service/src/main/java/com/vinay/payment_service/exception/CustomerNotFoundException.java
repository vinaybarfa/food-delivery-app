package com.vinay.payment_service.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(Long customerId) {
        super("Customer with Id '%id not found.".formatted(customerId));
    }

    public CustomerNotFoundException(String customerName) {
        super("Customer with Name %s not found".formatted(customerName));
    }
}
