package com.food_delivery.user_service.annotations;

import com.food_delivery.user_service.entity.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class UserRoleValidator implements ConstraintValidator<UserRoleValidation, Role> {
    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        if (role == null) return false;
        List<Role> allowRoles = List.of(Role.ADMIN, Role.CUSTOMER, Role.DELIVERY_BOY, Role.RESTAURANT_OWNER);
        return allowRoles.contains(role);
    }
}
