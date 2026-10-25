package com.vinay.payment_service.services;

import com.vinay.common.dto.OrderDto;
import com.vinay.common.dto.OrderItemDto;
import com.vinay.payment_service.config.logging.Loggable;
import com.vinay.payment_service.entities.Customer;
import com.vinay.payment_service.exception.CustomerNotFoundException;
import com.vinay.payment_service.respositories.CustomerRepository;
import com.vinay.payment_service.utils.AppConstants;
import com.vinay.payment_service.utils.LogSanitizer;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@Loggable
@RequiredArgsConstructor
public class PaymentOrderManageService {

    private static final Logger log = LoggerFactory.getLogger(PaymentOrderManageService.class);

    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;


    @Timed(percentiles = 1.0)
    public OrderDto reserve(OrderDto orderDto) {
        log.debug(
                "Reserving Order with Id : {} in payment service with payload {}",
                LogSanitizer.sanitizeForLog(String.valueOf(orderDto.orderId())),
                LogSanitizer.sanitizeForLog(String.valueOf(orderDto))
        );
        Optional<Customer> optionalCustomer = customerRepository.findById(orderDto.customerId());

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            log.info(
                    "Found Customer : {}",
                    LogSanitizer.sanitizeForLog(String.valueOf(customer.getId()))
            );
            var totalOrderPrice = orderDto.items().stream()
                    .map(OrderItemDto::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .doubleValue();

            if (totalOrderPrice <= customer.getAmountAvailable()) {
                orderDto = orderDto.withStatus("ACCEPT");
                customer.setAmountReserved(customer.getAmountReserved() + totalOrderPrice);
                customer.setAmountAvailable(customer.getAmountAvailable() - totalOrderPrice);
            } else {
                orderDto = orderDto.withStatus("REJECT");
            }
            log.info(
                    "Saving customer : {} after reserving",
                    LogSanitizer.sanitizeForLog(String.valueOf(customer))
            );
            customerRepository.save(customer);
            orderDto = orderDto.withSource(AppConstants.SOURCE);
            kafkaTemplate.send(
                    AppConstants.PAYMENT_ORDER_TOPIC,
                    String.valueOf(orderDto.orderId()),
                    orderDto
            );
            log.info(
                    "Send Reserved Order : {} to topic : {}",
                    LogSanitizer.sanitizeForLog(String.valueOf(orderDto)),
                    AppConstants.PAYMENT_ORDER_TOPIC
            );
        } else {
            log.info("Customer not found for id : {}", orderDto.customerId());
            throw new CustomerNotFoundException(orderDto.customerId());
        }

        return orderDto;
    }

    @Timed(percentiles = 1.0)
    public void confirm(OrderDto orderDto) {
        log.debug(
                "Confirming Order with Id : {} in payment service with payload {}",
                LogSanitizer.sanitizeForLog(String.valueOf(orderDto.orderId())),
                LogSanitizer.sanitizeForLog(String.valueOf(orderDto))
        );
        Customer customer = customerRepository.findById(orderDto.orderId())
                .orElseThrow(() -> new CustomerNotFoundException(orderDto.customerId()));
        log.info("Found Customer: {}", LogSanitizer.sanitizeForLog(String.valueOf(customer)));
        var orderPrice = orderDto.items().stream()
                .map(OrderItemDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .intValue();
        if (orderDto.status().equals("CONFIRMED")) {
            customer.setAmountAvailable(customer.getAmountReserved() - orderPrice);
        }else if (orderDto.status().equals(AppConstants.ROLLBACK)
            && !AppConstants.SOURCE.equals(orderDto.source()))
        {
            customer.setAmountReserved(customer.getAmountReserved() - orderPrice);
            customer.setAmountReserved(customer.getAmountAvailable() + orderPrice);
        }

        log.info(
                "Saving customer : {} After Configration",
                LogSanitizer.sanitizeForLog(String.valueOf(customer))
        );
        Customer saved = customerRepository.save(customer);
        log.debug("Saved customer : {}", LogSanitizer.sanitizeForLog(String.valueOf(saved)));
    }
}
