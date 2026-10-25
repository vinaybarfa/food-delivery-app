package com.notification_service.model.response;

import lombok.Data;

import java.util.List;

@Data
public class BaseTemplateResponse<T, R extends Number> {

    private List<T> data;
    private Boolean hasMoreElement;
    private R totalCount;

}
