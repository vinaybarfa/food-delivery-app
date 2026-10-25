package com.foodie.restaurant_service.service;

import com.foodie.restaurant_service.dto.CreateRestaurantRequestDTO;
import com.foodie.restaurant_service.dto.RestaurantDTO;

import java.util.List;
import java.util.Map;

public interface RestaurantService {

    RestaurantDTO createRestaurant(
            CreateRestaurantRequestDTO request
    ) throws Exception;

    RestaurantDTO updateRestaurant(
            Long restaurantId,
            Map<String, Object> updates
    ) throws Exception;

    void deleteRestaurant(
            Long restaurantId
    ) throws Exception;

    List<RestaurantDTO> getAllRestaurant();

    RestaurantDTO findRestaurantById(
            Long restaurantId
    ) throws Exception;

    RestaurantDTO findRestaurantByUserId(
            Long userId
    ) throws Exception;

    List<RestaurantDTO> searchRestaurant(
            String keyword
    ) throws Exception;

    RestaurantDTO updateRestaurantStatus(
            Long restaurantId
    ) throws Exception;
}
