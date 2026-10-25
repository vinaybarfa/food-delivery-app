package com.vinay.common_service.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentCancelEvent {

    private String paymentId;
    private Long orderId;
    private String paymentStatus;

}
