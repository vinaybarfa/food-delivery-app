package com.paypal.reward_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paypal.reward_service.dto.TransactionDTO;
import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.repository.RewardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class RewardConsumer {

    private final RewardRepository rewardRepository;
    private final ObjectMapper objectMapper;

    public RewardConsumer(RewardRepository rewardRepository, ObjectMapper objectMapper) {
        this.rewardRepository = rewardRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @KafkaListener(topics = "${reward.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumerTransaction(@Payload TransactionDTO transactionDTO) {
        try{
            if(rewardRepository.existsByTransactionId(transactionDTO.getTransactionId())) {
                log.info("Reward already exists for transaction: {}",transactionDTO.getStatus());
                System.out.println("Reward already exists for transaction: "+transactionDTO.getTransactionId());
            return;
            }else {
                Reward reward = Reward.builder()
                        .transactionId(transactionDTO.getTransactionId())
                        .userId(transactionDTO.getSenderId())
                        .points(transactionDTO.getAmount())
                        .rewardAssignDate(LocalDateTime.now())
                        .transactionId(transactionDTO.getTransactionId())
                        .build();
                rewardRepository.save(reward);
                System.out.println("Reward generate cuss");
            }
        }catch (Exception e) {
            System.out.println("Failed to process transaction "+transactionDTO.getTransactionId());
            throw e;
        }
    }
    
}