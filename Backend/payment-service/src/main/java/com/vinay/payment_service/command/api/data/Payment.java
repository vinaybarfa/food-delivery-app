package com.vinay.payment_service.command.api.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Builder
public class Payment {

    @Id
    private String paymentId;
    private Long orderId;
    private Date paymentDate;
    private String paymentStatus;
}
