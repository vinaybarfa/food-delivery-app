package com.notification_service.model.request;

import com.notification_service.constants.ErrorConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class SendNotificationRequest {

    @NotBlank(message = ErrorConstants.TEMPLATE_ID_IS_REQUIRED)
    private String templateId;
    private Map<String, Object> dynamicVariables;
    private NotificationType notificationType;

}
