package com.notification_service.model.context;

public record NotificationContext(String tenantId, boolean ignoreTenantIdInjection) {
}
