package com.notification_service.dao.impl;

import com.notification_service.dao.interfaces.TemplateDao;
import com.notification_service.dao.repository.TemplateRepository;
import com.notification_service.model.context.NotificationContextHolder;
import com.notification_service.model.entity.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

    private final TemplateRepository templateRepository;

    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName) {
        return templateRepository.findByNameIgnoreCaseAndTenantId(templateName, tenantId);
    }

    @Override
    public Optional<Template> findByTenantIdAndId(final String tenantId, final String id) {
        return templateRepository.findByIdAndTenantId(UUID.fromString(id), tenantId);
    }

    @Override
    public Template save(final Template template) {
        return templateRepository.save(template);
    }

    @Override
    public Page<Template> filterTemplates(final Example<Template> example, final Pageable pageable) {
        return templateRepository.findAll(example, pageable);
    }

    @Override
    public Page<Template> findAllTemplatesByCurrentTenant(final String name, final PageRequest pageRequest) {
        return findAllTemplates(name, getCurrentTenant());
    }

    private String getCurrentTenant() {
        return NotificationContextHolder.getContext().tenantId();
    }

    @Override
    public Page<Template> findAllTemplates(final String name, final String tenantId) {
        return null;
    }

    @Override
    public void deleteTemplateId(final String id) {
        templateRepository.deleteById(id);
    }
}
