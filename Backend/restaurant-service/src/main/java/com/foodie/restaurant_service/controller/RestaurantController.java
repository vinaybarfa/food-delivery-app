package com.foodie.restaurant_service.controller;

import com.foodie.restaurant_service.dto.RestaurantDTO;
import com.foodie.restaurant_service.service.RestaurantService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService
            restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantDTO>>
    searchRestaurant(

            @RequestParam String keyword

    ) throws Exception {

        return ResponseEntity

                .status(HttpStatus.OK)

                .body(
                        restaurantService
                                .searchRestaurant(
                                        keyword
                                )
                );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<RestaurantDTO>
    findRestaurantByUserId(

            @PathVariable Long userId

    ) throws Exception {

        return ResponseEntity

                .status(HttpStatus.OK)

                .body(
                        restaurantService
                                .findRestaurantByUserId(
                                        userId
                                )
                );
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>>
    getAllRestaurant() {

        return ResponseEntity

                .status(HttpStatus.OK)

                .body(
                        restaurantService
                                .getAllRestaurant()
                );
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO>
    getRestaurantById(

            @PathVariable Long restaurantId

    ) throws Exception {

        return ResponseEntity

                .status(HttpStatus.OK)

                .body(
                        restaurantService
                                .findRestaurantById(
                                        restaurantId
                                )
                );
    }
}
