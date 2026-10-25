package com.sevice.order.command.api.event;

import com.sevice.order.dto.OrderItemDTO;
import com.vinay.common_service.events.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreatedEvent {

    private Long orderId;
    private Long customerId;
    private Long restaurantId;
    private OrderStatus status;
    private List<OrderItemDTO> items;

}
