package com.vinay.payment_service.command.api.aggregate;

import com.vinay.common_service.commands.ValidatePaymentCommand;
import com.vinay.common_service.events.PaymentCancelEvent;
import com.vinay.common_service.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
public class PaymentAggregate {     // Handle the Payment Validation

    @AggregateIdentifier
    private String paymentId;
    private Long orderId;
    private String paymentStatus;

    public PaymentAggregate() {

    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        // validate the Payment Details
        // Publish the Payment Processed event
        log.info("Executing ValidatePaymentCommand for " + "Order Id : {} and Payment Id", validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                .orderId(validatePaymentCommand.getOrderId())
                .paymentId(validatePaymentCommand.getPaymentId())
                .orderStatus(validatePaymentCommand.getOrderStatus())
                .build();

        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("Payment Process Event Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {   // update the Payment Handler
        this.paymentId = event.getPaymentId();
    }

    @EventSourcingHandler
    public void on(PaymentCancelEvent paymentCancelEvent) {
        this.paymentStatus = paymentCancelEvent.getPaymentStatus();
    }

}
