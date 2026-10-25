package com.sevice.order.entity;

import com.vinay.common_service.events.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long orderId;

    private Long customerId;

    private Long restaurantId;

    private Long addressId;

    private Long totalAmount;

    private Integer totalItem;

    private Integer totalPrice;

    private Long quantity;

    @Enumerated(
            EnumType.STRING
    )
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items =
            new ArrayList<>();
}