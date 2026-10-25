package com.vinay.ingredient_service.dto;

import com.vinay.ingredient_service.entity.IngredientsItem;
import lombok.Data;

@Data
public class IngredientsItemDTO {

    private Long id;
    private String name;
    private Integer quantity;
    private String type;
    private Boolean isStoke = true;
    private Long foodId;
    private Long restaurantId;
}
