package com.foodie.food_service.service.external;

import com.foodie.food_service.config.FeignConfig;
import com.foodie.food_service.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "restaurant-services", configuration = FeignConfig.class)
public interface RestaurantService {

    @GetMapping("/api/admin/restaurant/{id}")
    RestaurantDTO getRestaurantById(@RequestHeader("Authorization") String token, @PathVariable("id") Long restaurantId);     // We only have to Declare

    @GetMapping("/api/admin/restaurant/restaurants")
    List<RestaurantDTO> getAllRestaurant();

}
