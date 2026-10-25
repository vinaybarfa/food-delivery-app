package com.notification_service.model.request;

import com.notification_service.model.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TemplateFilterRequest extends BaseSearchDTO<Template> {

    private String name;

    @Override
    protected Class<Template> getEntityClass() {        // it provides Entity where we perform Search
        return Template.class;
    }


}
