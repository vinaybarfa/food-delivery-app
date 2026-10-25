package com.notification_service.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "templates")
@Builder
public class Template extends AbstractEntity{

    private String id;
    private String name;
    private Map<String, String> templateVariables;
    private String messageTemplate;
    private String tenantId;

}
