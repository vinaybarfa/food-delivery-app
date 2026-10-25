package com.vinay.common_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDeliveryEvent {
    private String deliveryId;
    private String deliveryStatus;
    private Long orderId;
}
