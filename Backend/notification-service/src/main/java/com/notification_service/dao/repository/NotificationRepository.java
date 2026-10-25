package com.notification_service.dao.repository;

import com.notification_service.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    Optional<List<Notification>> findByUserId(Long id);
}