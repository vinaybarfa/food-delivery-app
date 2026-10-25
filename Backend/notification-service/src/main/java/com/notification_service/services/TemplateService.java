package com.notification_service.services;

import com.notification_service.model.entity.Template;
import com.notification_service.model.request.CreateUpdateTemplateRequest;
import com.notification_service.model.request.TemplateFilterRequest;
import com.notification_service.model.request.UpdateTemplateRequest;
import com.notification_service.model.response.FilterTemplateResponse;
import com.notification_service.model.response.TemplateResponse;
import jakarta.validation.Valid;

public interface TemplateService {

     TemplateResponse createTemplate(CreateUpdateTemplateRequest request);


     FilterTemplateResponse filterTemplate(TemplateFilterRequest request);

     TemplateResponse updateTemplate(String id, @Valid UpdateTemplateRequest request);

     void deleteTemplate(String id);

     Template getTemplateForCurrentTenant(String id);
}
