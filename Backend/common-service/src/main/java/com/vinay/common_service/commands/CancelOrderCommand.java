package com.vinay.common_service.commands;

import com.vinay.common_service.events.OrderStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class CancelOrderCommand {

    @TargetAggregateIdentifier
    private Long orderId;
    private OrderStatus orderStatus;

    public CancelOrderCommand(Long orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
