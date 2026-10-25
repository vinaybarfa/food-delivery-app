package com.notification_service.model.request;

import lombok.Getter;

@Getter
public enum NotificationType {

    SMS("SMS"),
    EMAIL("EMAIL"),
    WEBHOOK("WEBHOOK");

    public final String value;

    NotificationType(String type) {
        this.value = type;
    }
}
