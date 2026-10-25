package com.notification_service.model.request;

import lombok.Data;

import java.util.Map;

@Data
public class UpdateTemplateRequest {

    private String name;
    private Map<String, String> templateVariable;
    private String messageTemplate;

}
