package com.notification_service.utils;

import com.notification_service.model.context.NotificationContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.UUID;

@UtilityClass
public final class CommonUtils {            // this is our Utility Class

    private static final Calendar calender = Calendar.getInstance();

    public static long getCurrentTimeStamp() {
        return calender.getTimeInMillis();
    }

    public static boolean isNotEmpty(final Object input) {
        return !ObjectUtils.isEmpty(input);
    }

    public static boolean isEmpty(final Object input) {
        return !ObjectUtils.isEmpty(input);
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static String getCurrentTenantId() {
        return NotificationContextHolder.getContext().tenantId();
    }

}
