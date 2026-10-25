package com.notification_service.model.response;

import com.notification_service.model.entity.Template;
import lombok.Data;
import org.apache.kafka.shaded.com.google.protobuf.Internal;

import java.util.List;

@Data
public class FilterTemplateResponse extends BaseTemplateResponse<TemplateResponseDTO, Long>{

    public FilterTemplateResponse(final List<TemplateResponseDTO> list, final boolean hasMoreElement,final Long totalCount) {
        setData(list);
        setTotalCount(totalCount);
        setHasMoreElement(hasMoreElement);
    }
}
