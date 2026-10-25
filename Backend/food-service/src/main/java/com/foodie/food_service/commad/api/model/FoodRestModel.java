package com.foodie.food_service.commad.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FoodRestModel {

    private Long foodId;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(length = 1000)
    @ElementCollection  // It will create a suppurate table for images
    private List<String> images;
    private Boolean isAvailable;
    private Long restaurantId;
    private Long orderItemId;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
    private Long ingredientsItemId;
    private Long cartItemId;
    private Long categoryId;
    private LocalDate date;

}
