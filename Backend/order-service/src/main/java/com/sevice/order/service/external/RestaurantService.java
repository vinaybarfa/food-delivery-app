package com.sevice.order.service.external;

import com.sevice.order.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RESTAURANT-SERVICE")
public interface RestaurantService {

    @GetMapping("/api/restaurant/{id}")
    RestaurantDTO getRestaurantById(@PathVariable("id") Long restaurantId);     // We only have to Declare

    @GetMapping("/restaurants")
    List<RestaurantDTO> getAllRestaurant();

}
