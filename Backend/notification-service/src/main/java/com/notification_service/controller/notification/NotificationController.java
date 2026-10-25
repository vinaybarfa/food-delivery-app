package com.notification_service.controller.notification;

import com.notification_service.model.Notification;
import com.notification_service.model.request.SendNotificationRequest;
import com.notification_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@RequestBody SendNotificationRequest notification) {
        notificationService.sendNotification(notification);
        return ResponseEntity.ok().body("Notification Send Successfully");
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<Notification>> getNotificationByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotificationByUserId(userId));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteNotificationById(@PathVariable String id) {
        notificationService.removeNotificationById(id);
        return ResponseEntity.noContent().build();
    }
}
