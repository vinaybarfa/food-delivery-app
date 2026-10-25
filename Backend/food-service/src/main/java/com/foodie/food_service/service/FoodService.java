package com.foodie.food_service.service;

import com.foodie.food_service.dto.CategoryDTO;
import com.foodie.food_service.dto.FoodDTO;
import com.foodie.food_service.dto.RestaurantDTO;
import com.foodie.food_service.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    FoodDTO createFood(CreateFoodRequest createFoodRequest, CategoryDTO category, RestaurantDTO restaurant);

    List<FoodDTO> getRestaurantsFood(String token, Long restaurantId, boolean isVegetarian, boolean isNonVegetarian, boolean isSeasonal, String foodCategory);

    FoodDTO findByFoodId(Long id) throws Exception;

    void deleteFood(Long id) throws Exception;

    List<FoodDTO> searchFood(String keyword);

    FoodDTO updateAvailabilityStatus(Long foodId) throws Exception;

    List<FoodDTO> getAllFoods();
}
