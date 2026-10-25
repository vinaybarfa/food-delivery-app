package com.vinay.common_service.commands;

import com.vinay.common_service.events.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CompleteDeliveryCommand {

    @TargetAggregateIdentifier
    private Long orderId;
    private OrderStatus orderStatus;

}
