package com.vinay.common_service.commands;

import com.vinay.common_service.events.OrderStatus;
import com.vinay.common_service.model.CardDetails;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private Long orderId;
    private CardDetails cardDetails;
    private OrderStatus orderStatus;
}
