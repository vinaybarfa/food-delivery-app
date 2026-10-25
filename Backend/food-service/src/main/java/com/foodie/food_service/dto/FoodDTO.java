package com.foodie.food_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private List<String> images;
    private Boolean isAvailable;
    private Long restaurantId;
    private Long orderItemId;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
    private Long ingredientsItemId;
    private Long cartItemId;
    private Long categoryId;
}
