package com.vinay.ingredient_service.service.external;

import com.vinay.ingredient_service.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "restaurant-services", url = "http://localhost:1011")
public interface RestaurantService {

    @GetMapping("/api/restaurant/{id}")
    RestaurantDTO getRestaurantById(@PathVariable("id") Long restaurantId);     // We only have to Declare

    @GetMapping("/restaurants")
    List<RestaurantDTO> getAllRestaurant();

}
