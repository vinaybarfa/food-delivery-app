package com.vinay.cart_service.service.external;

import com.vinay.cart_service.dto.FoodDTO;
import com.vinay.cart_service.request.CreateFoodRequest;
import com.vinay.cart_service.response.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "food-services", url = "http://food-service")
public interface FoodService {

    // -------- ADMIN APIs --------

    @PostMapping("/api/admin/food/add")
    FoodDTO createFood(@RequestBody CreateFoodRequest request);

    @GetMapping("/api/admin/food/get/{id}")
    FoodDTO getFoodById(@PathVariable("id") Long id);

    @DeleteMapping("/api/admin/food/remove/{id}")
    MessageResponse deleteFood(@PathVariable("id") Long id);

    @PutMapping("/api/admin/food/update-availability/{id}")
    FoodDTO updateAvailability(@PathVariable("id") Long id);


    // -------- USER APIs --------

    @GetMapping("/api/food/search")
    List<FoodDTO> searchFood(
            @RequestParam("keyword") String keyword
    );

    @GetMapping("/api/food/restaurant/{restaurantId}")
    List<FoodDTO> getRestaurantFood(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestParam("vegetarian") Boolean vegetarian,
            @RequestParam("nonVegetarian") Boolean nonVegetarian,
            @RequestParam("seasonal") Boolean seasonal,
            @RequestParam(value = "food_category", required = false) String foodCategory,
            @RequestHeader("Authorization") String jwt
    );
}
