package com.notification_service.kafka;

import com.notification_service.dao.repository.NotificationRepository;
import com.notification_service.model.Notification;
import com.notification_service.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    @KafkaListener(
            topics = "${notification.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(Transaction transaction) {

        try {

            log.info(
                    "Received transaction event. SenderId={}, ReceiverId={}, Amount={}",
                    transaction.getSenderId(),
                    transaction.getReceiverId(),
                    transaction.getAmount()
            );

            Notification notification = Notification.builder()
                    .userId(transaction.getReceiverId())
                    .senderId(transaction.getSenderId())
                    .message(
                            String.format(
                                    "₹ %.2f received from user %d",
                                    transaction.getAmount(),
                                    transaction.getSenderId()
                            )
                    )
                    .status("SENT")
                    .sendAt(LocalDateTime.now())
                    .build();

            Notification savedNotification =
                    notificationRepository.save(notification);

            log.info(
                    "Notification saved successfully. NotificationId={}",
                    savedNotification.getId()
            );

        } catch (Exception ex) {

            log.error(
                    "Failed to process transaction event : {}",
                    transaction,
                    ex
            );

            // Optional:
            // Publish failed event to notification-dlt topic
            // Send alert
            // Store failed notification
        }
    }
}