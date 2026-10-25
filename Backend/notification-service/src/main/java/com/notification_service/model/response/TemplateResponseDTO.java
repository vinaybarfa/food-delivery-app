package com.notification_service.model.response;

import com.notification_service.model.entity.Template;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
public class TemplateResponseDTO {
    private String id;
    private String name;
    private Map<String, String> templateVariable;

    public TemplateResponseDTO(final Template template) {
        BeanUtils.copyProperties(template, this);          // copy he template field in the TemplateResponseDTO
        setId(template.getId().toString());
    }
}
