package com.food_delivery.user_service.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)   // validate at runtime
@Constraint(validatedBy = {UserRoleValidator.class})        // this will validate through the UserRoleValidator class
public @interface UserRoleValidation {

    String message() default "Role should be relevant";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
