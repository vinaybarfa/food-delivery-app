package com.foodie.food_service.clientTest;

import com.foodie.food_service.client.RestaurantClient;
import com.foodie.food_service.dto.RestaurantDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
public class RestaurantClientImplTest {

    @Autowired
    private RestaurantClient restaurantClient;

    @Test
    public void getAllRestaurant() {
        List<RestaurantDTO> allRestaurant = restaurantClient.getAllRestaurant();
        allRestaurant.stream().peek(e -> {
            System.out.println(e);
        });

    }


}
