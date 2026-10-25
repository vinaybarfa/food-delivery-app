package com.vinay.payment_service.services.listener;

import com.vinay.common.dto.OrderDto;
import com.vinay.payment_service.config.logging.Loggable;
import com.vinay.payment_service.services.PaymentOrderManageService;
import com.vinay.payment_service.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@EnableKafka
@Loggable
@RequiredArgsConstructor
public class KafkaListenerConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaListenerConfig.class);

    private final PaymentOrderManageService paymentOrderManageService;

    private final CountDownLatch deadLetterLatch = new CountDownLatch(1);

    @RetryableTopic(
            backOff = @BackOff(delay = 1000, multiplier = 2.0),
            exclude = {ClassNotFoundException.class},
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_DELAY_VALUE
    )
    @KafkaListener(id = "orders", topics = AppConstants.ORDERS_TOPIC, groupId = "payment")
    public void onEvent(OrderDto orderDto) {
        log.info(
                "Received Order in payment service : {} from topic: {} with source: {}",
                orderDto,
                AppConstants.ORDERS_TOPIC,
                orderDto.source()
        );

        if ("NEW".equals(orderDto.status())) {
            paymentOrderManageService.reserve(orderDto);
        } else {
            paymentOrderManageService.confirm(orderDto);
        }
    }

    @DltHandler
    public void dlt(OrderDto orderDto, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("Received dead-letter message : {} from topic {}", orderDto, topic);
        deadLetterLatch.countDown();
    }

    public CountDownLatch getDeadLetterLatch() {
        return this.deadLetterLatch;
    }

}
