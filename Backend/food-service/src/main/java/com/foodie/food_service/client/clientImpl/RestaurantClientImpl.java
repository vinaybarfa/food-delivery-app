package com.foodie.food_service.client.clientImpl;

import com.foodie.food_service.client.RestaurantClient;
import com.foodie.food_service.dto.RestaurantDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantClientImpl implements RestaurantClient {

    private final RestClient restClient;

    @Override
    public List<RestaurantDTO> getAllRestaurant() {
        try {
            List<RestaurantDTO> restaurantDTOList = restClient.get()
                    .uri("/api/restaurant/restaurants")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return restaurantDTOList;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }


    }
}
