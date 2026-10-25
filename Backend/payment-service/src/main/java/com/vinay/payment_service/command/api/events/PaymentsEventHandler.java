package com.vinay.payment_service.command.api.events;

import com.vinay.common_service.events.PaymentCancelEvent;
import com.vinay.common_service.events.PaymentProcessedEvent;
import com.vinay.payment_service.command.api.data.Payment;
import com.vinay.payment_service.command.api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class PaymentsEventHandler {

    private final PaymentRepository paymentRepository;

    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {           // Here we Handle the Data and Store in the DataBase
        Payment payment = Payment.builder()
                .paymentId(paymentProcessedEvent.getPaymentId())
                .orderId(paymentProcessedEvent.getOrderId())
                .paymentStatus("COMPLETED")
                .paymentDate(new Date())
                .build();

        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelEvent paymentCancelEvent) {
        Payment payment = paymentRepository.findById(paymentCancelEvent.getPaymentId()).get();
        payment.setPaymentStatus(paymentCancelEvent.getPaymentStatus());
        paymentRepository.save(payment);
    }


}
