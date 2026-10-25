package com.notification_service.services.impl;

import com.notification_service.constants.ErrorConstants;
import com.notification_service.dao.interfaces.TemplateDao;
import com.notification_service.dao.repository.NotificationRepository;
import com.notification_service.exception.ValidationException;
import com.notification_service.model.Notification;
import com.notification_service.model.entity.Template;
import com.notification_service.model.request.SendNotificationRequest;
import com.notification_service.services.NotificationService;
import com.notification_service.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final TemplateDao templateDao;

    @Override
    public void sendNotification(final SendNotificationRequest request) {
        // Now we Validate the Template Dto
        Optional<Template> template = templateDao.findByTenantIdAndId(CommonUtils.getCurrentTenantId(), request.getTemplateId());
        if (template.isEmpty()) {
            // TODO send to audit topic
            throw new ValidationException(ErrorConstants.TEMPLATE_NOT_EXISTS_WITH_ID_ERROR, HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public List<Notification> getNotificationByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Notification is not found"));

    }

    @Override
    public void removeNotificationById(String id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void removeAllNotification() {
        notificationRepository.deleteAll();
    }
}
