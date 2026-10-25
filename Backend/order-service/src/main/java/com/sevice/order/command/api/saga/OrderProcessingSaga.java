package com.sevice.order.command.api.saga;

import com.sevice.order.command.api.event.OrderCreatedEvent;
import com.vinay.common_service.commands.CancelOrderCommand;
import com.vinay.common_service.commands.CompleteDeliveryCommand;
import com.vinay.common_service.events.*;
import com.vinay.common_service.commands.DeliverOrderCommand;
import com.vinay.common_service.commands.ValidatePaymentCommand;
import com.vinay.common_service.model.User;
import com.vinay.common_service.quries.GetUserPaymentDetailsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
@RequiredArgsConstructor
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;


    @StartSaga          // we have to maintain the Starting and Ending Point of Saga Pattern
    @SagaEventHandler(associationProperty = "orderId")
    private void handleSagaEvent(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for Order Id : {}", event.getOrderId());

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery =
                new GetUserPaymentDetailsQuery(event.getCustomerId());          // Here we are Getting the Query

        User user = null;

        try {
            user = queryGateway.query(
                    getUserPaymentDetailsQuery,
                    ResponseTypes.instanceOf(User.class)
            ).join();       // Here we pass the Query in the Gateway if exists otherwise throw an exception
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the Compensating Transaction
            cancelOrderCommand(event.getOrderId(), event.getStatus());
        }
        ValidatePaymentCommand validatePaymentCommand =
                ValidatePaymentCommand.builder()
                        .cardDetails(user.getCardDetails())
                        .orderId(event.getOrderId())
                        .paymentId(UUID.randomUUID().toString())
                        .build();

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    private void cancelOrderCommand(Long orderId, OrderStatus orderStatus) {
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId, orderStatus);
        commandGateway.send(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {           // when the PaymentProcessedEvent occurred then we Handling the Event in SAGA Design Pattern Handler
        log.info("PaymentProcessedEvent in Saga for Order Id : {}", event.getOrderId());

        try {

            if (true) {
                throw new Exception();
            }
            
            DeliverOrderCommand shipOrderCommand = DeliverOrderCommand.builder().build().builder()      // Here we create a Command
                    .orderId(event.getOrderId())
                    .deliverId(UUID.randomUUID().toString())
                    .build();

            commandGateway.sendAndWait(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start Compensating transaction
        }

    }


    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderDeliveryEvent event) {
        log.info("OrderDeliveryEvent in Saga for Order Id : {}", event.getOrderId());

        CompleteDeliveryCommand completeDeliveryCommand =
                CompleteDeliveryCommand.builder()
                        .orderId(event.getOrderId())
                        .orderStatus(OrderStatus.APPROVED)
                        .build();

        commandGateway.send(completeDeliveryCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga    // this will end the SAGA
    public void handle(OrderCompleteEvent event) {
        log.info("OrderCompleteEvent in Saga for Order Id : {}", event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCancelledEvent event) {
        log.info("OrderCancelledEvent in Saga for Order Id : {}", event.getOrderId());

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelEvent event) {
        log.info("PaymentCancelEvent in Saga for Order Id : {}", event.getOrderId());
    }


}

