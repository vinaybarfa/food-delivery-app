package com.food_delivery.user_service.controller;

import com.food_delivery.user_service.entity.User;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/audit")
public class AuditController {

    private final EntityManagerFactory entityManagerFactory;

    @GetMapping(path = "/user/{userId}")
    public List<User> getUserRevision(@PathVariable Integer userId) {
        AuditReader reader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
        List<Number> revisions = reader.getRevisions(User.class, userId);
        return revisions
                .stream()
                .map(revisionNumber -> reader.find(User.class, userId,revisionNumber))
                .collect(Collectors.toList());
    }
}
