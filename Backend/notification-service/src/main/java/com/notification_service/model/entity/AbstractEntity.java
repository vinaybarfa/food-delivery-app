package com.notification_service.model.entity;

import com.notification_service.utils.CommonUtils;
import lombok.Data;

@Data
public abstract class AbstractEntity {          // we always put our common data in the Common class and this class when needed

    private Long createdAt;
    private Long updatedAt;

    public void entityCreated() {
        setCreatedAt(CommonUtils.getCurrentTimeStamp());
        setUpdatedAt(CommonUtils.getCurrentTimeStamp());
    }

    public void entityUpdated() {
        setUpdatedAt(CommonUtils.getCurrentTimeStamp());
    }


}
