package com.vinay.ingredient_service.service;

import com.vinay.ingredient_service.dto.IngredientCategoryDTO;
import com.vinay.ingredient_service.dto.IngredientsItemDTO;

import java.util.List;

public interface IngredientService {

    IngredientCategoryDTO createIngredientCategory(String name, Long restaurantId) throws Exception;
    IngredientCategoryDTO findIngredientCategoryById(Long id) throws Exception;
    List<IngredientCategoryDTO> findIngredientCategoryByRestaurantId(Long id) throws Exception;
    IngredientsItemDTO createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;
    List<IngredientsItemDTO> findRestaurantIngredients(Long restaurantId);
    IngredientsItemDTO updateStock(Long id);

}
