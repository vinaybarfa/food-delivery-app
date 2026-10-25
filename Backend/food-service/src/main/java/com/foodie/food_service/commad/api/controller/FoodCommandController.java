package com.foodie.food_service.commad.api.controller;

import com.foodie.food_service.commad.api.commands.CreateFoodCommand;
import com.foodie.food_service.commad.api.model.FoodRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/food")
public class FoodCommandController {

    private CommandGateway commandGateway;

    @PostMapping
    public String addFood(@RequestBody FoodRestModel foodRestModel) {

        CreateFoodCommand createFoodCommand = CreateFoodCommand.builder()
                .foodId(foodRestModel.getFoodId())
                .name(foodRestModel.getName())
                .price(foodRestModel.getPrice())
                .date(foodRestModel.getDate())
                .cartItemId(foodRestModel.getCartItemId())
                .foodId(foodRestModel.getFoodId())
                .description(foodRestModel.getDescription())
                .isSeasonal(foodRestModel.getIsSeasonal())
                .orderItemId(foodRestModel.getOrderItemId())
                .images(foodRestModel.getImages())
                .isVegetarian(foodRestModel.getIsVegetarian())
                .categoryId(foodRestModel.getCategoryId())
                .restaurantId(foodRestModel.getRestaurantId())
                .build();

        String result = commandGateway.sendAndWait(createFoodCommand);
        return result;
    }
}
