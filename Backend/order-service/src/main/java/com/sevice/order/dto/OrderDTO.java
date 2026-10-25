package com.sevice.order.dto;

import com.sevice.order.entity.OrderItem;
import com.vinay.common_service.events.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private List<OrderItem> items = new ArrayList<>();
    private Integer totalItem;
    private Integer totalPrice;
    private Long restaurnatId;
    private Long userId;
    private Long AddressId;
}
