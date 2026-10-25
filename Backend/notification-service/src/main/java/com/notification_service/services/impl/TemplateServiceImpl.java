package com.notification_service.services.impl;

import com.notification_service.constants.ErrorConstants;
import com.notification_service.dao.interfaces.TemplateDao;
import com.notification_service.exception.ValidationException;
import com.notification_service.model.context.NotificationContext;
import com.notification_service.model.context.NotificationContextHolder;
import com.notification_service.model.entity.Template;
import com.notification_service.model.request.CreateUpdateTemplateRequest;
import com.notification_service.model.request.TemplateFilterRequest;
import com.notification_service.model.request.UpdateTemplateRequest;
import com.notification_service.model.response.FilterTemplateResponse;
import com.notification_service.model.response.TemplateResponse;
import com.notification_service.model.response.TemplateResponseDTO;
import com.notification_service.services.TemplateService;
import com.notification_service.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateServiceImpl implements TemplateService {

    private final TemplateDao templateDao;

    @Override
    public TemplateResponse createTemplate(CreateUpdateTemplateRequest request) {
        NotificationContext context = NotificationContextHolder.getContext();

        templateDao.findByTenantIdAndName(CommonUtils.getCurrentTenantId(), request.getName()).ifPresent(template -> {
            throw new ValidationException(ErrorConstants.TEMPLATE_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST.value());
        });
        Template template = Template.builder()
                .id(CommonUtils.generateUUID())
                .tenantId(CommonUtils.getCurrentTenantId())
                .build();
        BeanUtils.copyProperties(request, template);
        template.entityCreated();
        templateDao.save(template);
        return new TemplateResponse(template);
    }

    @Override
    public FilterTemplateResponse filterTemplate(final TemplateFilterRequest filterRequest) {
        NotificationContextHolder.ignoreTenantIdInjection();
        Page<Template> templates = templateDao.filterTemplates(filterRequest.buildSearchItem(), filterRequest.buildPageRequest());
        List<TemplateResponseDTO> data = templates.stream().map(TemplateResponseDTO::new).toList();
        return new FilterTemplateResponse(data, templates.hasNext(), templates.getTotalElements());
    }

    @Override
    public TemplateResponse updateTemplate(String id, UpdateTemplateRequest request) {
        Template template = getTemplateForCurrentTenant(id);

        if (CommonUtils.isNotEmpty(request.getName())) {        // if same name exists of  tenant in the DataBase then throw
            templateDao.findByTenantIdAndName(CommonUtils.getCurrentTenantId(), request.getName()).ifPresent(
                    template1 -> {
                        throw new ValidationException(ErrorConstants.TEMPLATE_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST.value());
                    });
            template.setName(request.getName());
        }

        if (CommonUtils.isNotEmpty(request.getMessageTemplate())) {
            template.setMessageTemplate(request.getMessageTemplate());
        }
        if (CommonUtils.isNotEmpty(request.getTemplateVariable())) {
            template.setTemplateVariables(request.getTemplateVariable());
        }

        templateDao.save(template);   // if changes(updates) are perform in then save in the db otherwise throw exception
        return new TemplateResponse(template);
    }

    @Override
    public void deleteTemplate(final String id) {
        getTemplateForCurrentTenant(id);
        templateDao.deleteTemplateId(id);
    }

    @Override
    public Template getTemplateForCurrentTenant(final String id) {
        return templateDao.findByTenantIdAndId(CommonUtils.getCurrentTenantId(), id).orElseThrow(() -> new ValidationException(ErrorConstants.TEMPLATE_NOT_EXISTS_WITH_ID_ERROR, HttpStatus.BAD_REQUEST.value()));
    }


}
