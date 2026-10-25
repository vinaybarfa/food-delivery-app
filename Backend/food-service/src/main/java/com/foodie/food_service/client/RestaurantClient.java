package com.foodie.food_service.client;

import com.foodie.food_service.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantClient {

    List<RestaurantDTO> getAllRestaurant();

}
