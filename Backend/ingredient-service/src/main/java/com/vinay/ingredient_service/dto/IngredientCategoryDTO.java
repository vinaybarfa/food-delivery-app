package com.vinay.ingredient_service.dto;

import com.vinay.ingredient_service.entity.IngredientsItem;
import lombok.Data;

@Data
public class IngredientCategoryDTO {

    private Long id;
    private String name;
    private Long restaurantId;
    private Long categoryId;
    private IngredientsItem ingredientsItem;

}
