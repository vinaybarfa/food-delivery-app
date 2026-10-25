package com.sevice.order.event;

import com.vinay.common_service.events.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackingEvent {

    private Long orderId;

    private Long customerId;

    private OrderStatus orderStatus;

    private LocalDateTime timestamp;
}