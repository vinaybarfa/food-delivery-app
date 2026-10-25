package com.vinay.payment_service.command.api.repository;

import com.vinay.payment_service.command.api.data.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}