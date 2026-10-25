package com.notification_service.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class CreateUpdateTemplateRequest {

    @NotBlank(message = "Name Field is Required")
    private String name;
    private Map<String, String> templateVariable;
    @NotBlank(message = "Message Template Field is Required")
    private String messageTemplate;

}
