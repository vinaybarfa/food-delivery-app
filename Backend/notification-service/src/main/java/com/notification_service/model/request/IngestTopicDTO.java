package com.notification_service.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class IngestTopicDTO {

    private String request;
    private String tenantId;
    private Long receivedId;
    private String templateId;
    private Map<String, Object> dynamicVariables;
    private NotificationType notificationType;

}
