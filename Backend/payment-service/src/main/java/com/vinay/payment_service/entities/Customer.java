package com.vinay.payment_service.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer {
    Long id;
    String name;
    String email;
    String address;
    String phone;
    double amountAvailable;
    double amountReserved;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getAmountAvailable() {
        return amountAvailable;
    }

    public double getAmountReserved() {
        return amountReserved;
    }

    public Customer setId(final Long id) {
        this.id = id;
        return this;
    }

    public Customer setName(final String name) {
        this.name = name;
        return this;
    }

    public Customer setEmail(final String email) {
        this.email = email;
        return this;
    }

    public Customer setAddress(final String address) {
        this.address = address;
        return this;
    }

    public Customer setPhone(final String phone) {
        this.phone = phone;
        return this;
    }

    public Customer setAmountAvailable(final double amountAvailable) {
        this.amountAvailable = amountAvailable;
        return this;
    }

    public Customer setAmountReserved(final double amountReserved) {
        this.amountReserved = amountReserved;
        return this;
    }
}
