package com.vinay.delivery_service.command.api.repository;

import com.vinay.delivery_service.command.api.data.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
}