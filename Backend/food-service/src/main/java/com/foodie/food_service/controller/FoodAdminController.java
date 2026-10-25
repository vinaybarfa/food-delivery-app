package com.foodie.food_service.controller;


import com.foodie.food_service.dto.CategoryDTO;
import com.foodie.food_service.dto.FoodDTO;
import com.foodie.food_service.dto.RestaurantDTO;
import com.foodie.food_service.request.CreateFoodRequest;
import com.foodie.food_service.response.MessageResponse;
import com.foodie.food_service.service.FoodService;
import com.foodie.food_service.service.external.CategoryService;
import com.foodie.food_service.service.external.RestaurantService;
import com.foodie.food_service.service.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/food")
public class FoodAdminController {

    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<FoodDTO> createFood(@RequestHeader("Authorization") String token, @RequestBody CreateFoodRequest createFoodRequest) throws Exception {
        RestaurantDTO restaurant = restaurantService.getRestaurantById(token, createFoodRequest.getRestaurantId());
        CategoryDTO category = categoryService.getCategoryById(createFoodRequest.getCategoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(createFoodRequest, category, restaurant));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FoodDTO> findByFoodById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.findByFoodId(id));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id) throws Exception {
        foodService.deleteFood(id);
        MessageResponse message = MessageResponse.builder()
                .message("Food Removed Successfully").build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

    @PutMapping("/update-availability/{id}")
    public ResponseEntity<FoodDTO> updateFoodAvailability(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(foodService.updateAvailabilityStatus(id));
    }


}
