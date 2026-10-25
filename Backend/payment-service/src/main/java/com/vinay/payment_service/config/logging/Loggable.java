package com.vinay.payment_service.config.logging;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE, METHOD})
public @interface Loggable {        // Here we Define Our Own Custom Annotation

    boolean params() default true;

    boolean result() default true;

    LogLevel value() default LogLevel.DEBUG;
}
