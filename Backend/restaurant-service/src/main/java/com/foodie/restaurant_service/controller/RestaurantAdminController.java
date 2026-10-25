package com.foodie.restaurant_service.controller;

import com.foodie.restaurant_service.dto.CreateRestaurantRequestDTO;
import com.foodie.restaurant_service.dto.RestaurantDTO;
import com.foodie.restaurant_service.response.MessageResponse;
import com.foodie.restaurant_service.service.RestaurantService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurant")
public class RestaurantAdminController {

    private final RestaurantService
            restaurantService;

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

    @PostMapping("/add")
    public ResponseEntity<RestaurantDTO>
    addRestaurant(

            @RequestBody
            CreateRestaurantRequestDTO request

    ) throws Exception {

        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(
                        restaurantService
                                .createRestaurant(
                                        request
                                )
                );
    }

    @PatchMapping("/update/{restaurantId}")
    public ResponseEntity<RestaurantDTO>
    updateRestaurant(

            @PathVariable Long restaurantId,

            @RequestBody
            Map<String, Object> updates

    ) throws Exception {

        return ResponseEntity

                .status(HttpStatus.OK)

                .body(
                        restaurantService
                                .updateRestaurant(
                                        restaurantId,
                                        updates
                                )
                );
    }

    @DeleteMapping("/delete/{restaurantId}")
    public ResponseEntity<MessageResponse>
    deleteRestaurant(

            @PathVariable Long restaurantId

    ) throws Exception {

        restaurantService.deleteRestaurant(
                restaurantId
        );

        return ResponseEntity.ok(

                MessageResponse.builder()

                        .message(
                                "Restaurant Deleted Successfully"
                        )

                        .build()
        );
    }

    @PutMapping("/updateStatus/{restaurantId}")
    public ResponseEntity<RestaurantDTO>
    updateRestaurantStatus(

            @PathVariable Long restaurantId

    ) throws Exception {

        return ResponseEntity

                .status(HttpStatus.OK)

                .body(
                        restaurantService
                                .updateRestaurantStatus(
                                        restaurantId
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

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO>
    getRestaurantById(

            @PathVariable Long id

    ) throws Exception {

        return ResponseEntity.ok(

                restaurantService
                        .findRestaurantById(id)
        );
    }
}
