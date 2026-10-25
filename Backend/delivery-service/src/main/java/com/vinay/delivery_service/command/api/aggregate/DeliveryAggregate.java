package com.vinay.delivery_service.command.api.aggregate;

import com.vinay.common_service.commands.DeliverOrderCommand;
import com.vinay.common_service.events.OrderDeliveryEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAggregate {

    @AggregateIdentifier
    private String deliverId;
    private Long orderId;
    private String deliveryStatus;

    @CommandHandler
    public DeliveryAggregate(DeliverOrderCommand deliverOrderCommand) {     // publish the Event
        // Validate the command
        // Publish the Order Deliver Event

        OrderDeliveryEvent orderDeliverEvent = OrderDeliveryEvent.builder()
                .deliveryId(deliverOrderCommand.getDeliverId())
                .orderId(deliverOrderCommand.getOrderId())
                .deliveryStatus("COMPLETED")
                .build();

        AggregateLifecycle.apply(orderDeliverEvent);
    }

    @EventSourcingHandler
    public void on(OrderDeliveryEvent event) {       // Update the Event in the Event Sourcing
        this.deliverId = event.getDeliveryId();
        this.deliveryStatus = event.getDeliveryStatus();
        this.orderId = event.getOrderId();
    }
}
