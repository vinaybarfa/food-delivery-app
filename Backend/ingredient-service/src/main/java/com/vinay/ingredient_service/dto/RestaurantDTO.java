package com.vinay.ingredient_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Embeddable
public class RestaurantDTO {

    private Long id;
    private String title;

    private String description;
    private String cuisineType;
    private String rating;
    private String openingHours;
    private LocalTime openingTime;
    private LocalTime closingTime;

    @Column(length = 1000)
    private List<String> images;
    private Boolean isOpen = true;
    private LocalDateTime createdDate;





}
