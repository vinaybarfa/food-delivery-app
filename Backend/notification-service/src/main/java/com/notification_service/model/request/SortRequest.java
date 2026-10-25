package com.notification_service.model.request;

import lombok.Data;

@Data
public class SortRequest {

    private String sortKey;
    private SortType sortType;
}
