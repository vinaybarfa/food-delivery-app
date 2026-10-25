package com.notification_service.dao.repository;

import com.notification_service.model.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository extends MongoRepository<Template, String> {


    Optional<Template> findByNameIgnoreCaseAndTenantId(String templateName, String uuid);

    Optional<Template> findByIdAndTenantId(UUID id, String tenantId);
}
