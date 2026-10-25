package com.notification_service.dao.interfaces;

import com.notification_service.model.entity.Template;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TemplateDao {
    Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName);

    Optional<Template> findByTenantIdAndId(final String tenantId, final String id);

    Template save(Template template);

    Page<Template> filterTemplates(final Example<Template> example, final Pageable pageable);

    Page<Template> findAllTemplatesByCurrentTenant(final String name, final PageRequest pageRequest);

    Page<Template> findAllTemplates(final String name, final String tenantId);

    void deleteTemplateId(String id);
}
