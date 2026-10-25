package com.foodie.food_service.controller;

import com.foodie.food_service.dto.FoodDTO;
import com.foodie.food_service.service.FoodService;
import com.foodie.food_service.service.external.RestaurantService;
import com.foodie.food_service.service.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {


    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @GetMapping("/all")
    public ResponseEntity<List<FoodDTO>> getAllFoods() {

        return ResponseEntity.ok(
                foodService.getAllFoods()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodDTO>> searchFood(
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        return ResponseEntity.ok(
                foodService.searchFood(keyword)
        );
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodDTO>> getRestaurantFood(
            @RequestHeader("Authorization") String token,
            @RequestParam Boolean vegetarian,
            @RequestParam Boolean seasonal,
            @RequestParam Boolean nonVegetarian,
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String food_category
    ) throws Exception {

        return ResponseEntity.ok(
                foodService.getRestaurantsFood(
                        token,
                        restaurantId,
                        vegetarian,
                        nonVegetarian,
                        seasonal,
                        food_category
                )
        );
    }

}
