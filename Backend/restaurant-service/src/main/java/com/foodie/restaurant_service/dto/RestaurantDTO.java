package com.foodie.restaurant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private Long id;

    private String name;

    private String description;

    private String rating;

    private String cuisineType;

    private String openingHours;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private List<String> images;

    private LocalDateTime registrationDate;

    private Boolean isOpen;

    private Long userId;
}
