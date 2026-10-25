package com.notification_service.model;

import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private Long senderId;

    private Long receiverId;

    @Positive(message = "Amount must be positive")
    private Double amount;

    private LocalDateTime timestamp;

    private String status;

    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }

        if (status == null) {
            status = "PENDING";
        }
    }
}