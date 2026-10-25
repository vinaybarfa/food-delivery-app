package com.sevice.order.command.api.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateOrderCommand {       // for creating a Command we need payload

    @TargetAggregateIdentifier   // the orderId is handle by Aggregate it will identify by Aggregate
    private String orderId;
    private String foodId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String  orderStatus;

}
