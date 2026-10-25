package com.vinay.common_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompleteEvent {

    private Long orderId;
    private OrderStatus orderStatus;
}
