package com.notification_service.services;

import com.notification_service.model.Notification;
import com.notification_service.model.request.SendNotificationRequest;

import java.util.List;

public interface NotificationService {

    void sendNotification(SendNotificationRequest notification);

    public List<Notification> getNotificationByUserId(Long userId);

    void removeNotificationById(String id);

    void removeAllNotification();
}
