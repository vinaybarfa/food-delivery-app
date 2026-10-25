package com.notification_service.model.request;

import com.notification_service.exception.ValidationException;
import com.notification_service.model.context.NotificationContextHolder;
import com.notification_service.utils.CommonUtils;
import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.UUID;

import static com.notification_service.utils.CommonUtils.isNotEmpty;
import static java.util.Optional.ofNullable;

@Data
public abstract class BaseSearchDTO<T> {

    private Integer page = 0;       // page, size are Optional(user provide or not provide)
    private Integer size = 10;
    private SortRequest sortRequest;

    public PageRequest buildPageRequest() {


        Sort sort = ofNullable(sortRequest)     // if sortRequest is available then work
                .filter(CommonUtils::isNotEmpty)        // if not empty
                .filter(req -> isNotEmpty(req.getSortKey()) && isNotEmpty(req.getSortType()))
                .filter(req -> {        // the coming request Data isValid(Here we basically validate the request)
                    try {
                        this.getClass().getDeclaredField(req.getSortKey());
                        return true;
                    } catch (NoSuchFieldException e) {
                        throw new ValidationException("Invalid Sort Key", HttpStatus.BAD_REQUEST.value());
                    }
                }).map(req -> Sort.by(Sort.Direction.fromString(req.getSortType().getValue()), req.getSortKey()))
                .orElse(Sort.by((Sort.Direction.DESC), "createdAt"));

        return PageRequest.of(ofNullable(page).orElse(0), ofNullable(page).orElse(10), sort);
    }

    public Example<T> buildSearchItem() {
        try {
            Class<T> clazz = getEntityClass();      // here we get Template class
            T instance = clazz.getConstructor().newInstance();      // now we get the Template Object
            injectTenantId(instance);       // here we inject the tenant Id the Template instance

           // Here we perform Searching
            for (Field dtoField : instance.getClass().getDeclaredFields()) {        // we iterate in all field of TemplateFilterRequest class
                dtoField.setAccessible(true);   // if we get the field then we make it accessible because fields are private with the help of Reflection
                Object fieldValue = dtoField.get(this); // here we get the search keyword which are send in the query param like(for - amazon => am or azon) check isExists in the Entity class
                if (isNotEmpty(fieldValue)) {
                    Field entityField = getField(clazz, dtoField.getName());
                    entityField.setAccessible(true);
                    entityField.set(instance, fieldValue);      // set the field in Entity
                }
            }

            ExampleMatcher exampleMatcher = ExampleMatcher          // which Operation we want to perform when
                    .matching()
                    .withIgnoreCase()
                    .withIncludeNullValues();

            return Example.of(instance, exampleMatcher);
        } catch (Exception ignored) {
            throw new ValidationException("Error while Building", HttpStatus.BAD_REQUEST.value());
        }
    }

    public Field getField(final Class<?> clazz, final String name) {       // check in field of class
        Class<?> current = clazz;
        while (current != null) {
            try {
                current.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();      // if field not exists then check in Super class
            }
        }

        return null;
    }


    private void injectTenantId(final Object instance) {
       if (NotificationContextHolder.getContext().ignoreTenantIdInjection()) {
           System.out.println("Ignoring Tenant Id injection");
           return;
       }
        try {
            Field tenantIdField = getField(instance.getClass(), "tenantId");
            tenantIdField.setAccessible(true);
            tenantIdField.set(instance, UUID.fromString(CommonUtils.getCurrentTenantId()));
        } catch (IllegalAccessException e) {
            throw new ValidationException("Error while setting a Tenant Id in the Search Builder", HttpStatus.BAD_REQUEST.value());
        }
    }

    protected abstract Class<T> getEntityClass();

}
