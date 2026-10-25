package com.vinay.common_service.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeliverOrderCommand {

    @TargetAggregateIdentifier
    private String deliverId;
    private Long orderId;

}
