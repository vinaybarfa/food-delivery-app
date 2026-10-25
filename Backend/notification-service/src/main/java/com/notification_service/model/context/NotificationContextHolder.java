package com.notification_service.model.context;

public final  class NotificationContextHolder {            // immutable class

    private static final ThreadLocal<NotificationContext> NOTIFICATION_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setContext(NotificationContext context) {
        NOTIFICATION_CONTEXT_THREAD_LOCAL.set(context);
    }

    public static NotificationContext getContext() {
        return NOTIFICATION_CONTEXT_THREAD_LOCAL.get();
    }

    public static void clear() {           // When in thread local you get and set the Data in ThreadLocal then we clear the context so there is not memory lick
        NOTIFICATION_CONTEXT_THREAD_LOCAL.remove();
    }

    // With the help of Context We can control the tenantId injection
    public static void ignoreTenantIdInjection() {
        String tenantId = NOTIFICATION_CONTEXT_THREAD_LOCAL.get().tenantId();
        NOTIFICATION_CONTEXT_THREAD_LOCAL.set(new NotificationContext(tenantId, true ));
    }

    public static void ignoreTenantIdInjection(final boolean input) {
        String tenantId = NOTIFICATION_CONTEXT_THREAD_LOCAL.get().tenantId();
        NOTIFICATION_CONTEXT_THREAD_LOCAL.set(new NotificationContext(tenantId, input));
    }

}
