package com.notification_service.model.response;

import com.notification_service.model.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponse {

    private String id;
    private String name;

    public TemplateResponse(final Template template) {      // here we do constructor overloading
        setId(template.getId().toString());
        setName(template.getName());
    }
}
