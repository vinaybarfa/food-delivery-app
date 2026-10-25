package com.notification_service.controller.template;

import com.notification_service.model.request.CreateUpdateTemplateRequest;
import com.notification_service.model.request.TemplateFilterRequest;
import com.notification_service.model.request.UpdateTemplateRequest;
import com.notification_service.model.response.FilterTemplateResponse;
import com.notification_service.model.response.TemplateResponse;
import com.notification_service.services.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/template")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping(value = "/create")
    public ResponseEntity<TemplateResponse> createTemplate(@Valid @RequestBody CreateUpdateTemplateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(templateService.createTemplate(request));
    }

//    @GetMapping(value = "/findAll")
//    public ResponseEntity<FilterTemplateResponse> findAllTemplates(TemplateFilterRequest filterRequest) {
//        return ResponseEntity.status(HttpStatus.OK).body(templateService.findAllTemplates(filterRequest));
//    }

    @GetMapping
    public ResponseEntity<FilterTemplateResponse> filterTemplate(TemplateFilterRequest request) {
        return ResponseEntity.ok().body(templateService.filterTemplate(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(@PathVariable String id, @Valid @RequestBody UpdateTemplateRequest request) {
        return ResponseEntity.ok().body(templateService.updateTemplate(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Template Deleted Successfully");
    }
}
