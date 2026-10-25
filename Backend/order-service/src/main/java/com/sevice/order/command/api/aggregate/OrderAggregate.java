package com.sevice.order.command.api.aggregate;

import com.sevice.order.command.api.command.CreateOrderCommand;
import com.sevice.order.command.api.event.OrderCreatedEvent;
import com.sevice.order.dto.OrderItemDTO;
import com.vinay.common_service.commands.CancelOrderCommand;
import com.vinay.common_service.commands.CompleteDeliveryCommand;
import com.vinay.common_service.events.OrderCancelledEvent;
import com.vinay.common_service.events.OrderCompleteEvent;
import com.vinay.common_service.events.OrderStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private Long orderId;
    private List<OrderItemDTO> items;
    private OrderStatus status;
    private Long customerId;
    private Long restaurantId;


    public OrderAggregate() {

    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {      // this will help to register the command
        // Validate the Command
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);        // here we apply the event
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {   // we update the Event Souring of OrderCreatedEvent
        this.status = orderCreatedEvent.getStatus();
        this.orderId = orderCreatedEvent.getOrderId();
        this.customerId = orderCreatedEvent.getCustomerId();
        this.items = orderCreatedEvent.getItems();
        this.restaurantId = orderCreatedEvent.getRestaurantId();
    }

    @CommandHandler
    public void handle(CompleteDeliveryCommand completeDeliveryCommand) {
        // Validate the Command, if fine then Publish the Order Complete Event

        OrderCompleteEvent orderCompleteEvent =
                OrderCompleteEvent.builder()
                        .orderId(completeDeliveryCommand.getOrderId())
                        .orderStatus(completeDeliveryCommand.getOrderStatus())
                        .build();

        AggregateLifecycle.apply(orderCompleteEvent);       // Publish the Event
    }

    @EventSourcingHandler
    public void on(OrderCompleteEvent event) {          // Update the Event-Sourcing
        this.status = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand) {
        OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();
        BeanUtils.copyProperties(cancelOrderCommand, orderCancelledEvent);

        AggregateLifecycle.apply(orderCancelledEvent);
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.status = event.getOrderStatus();
    }
}
