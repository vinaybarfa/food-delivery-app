package com.foodie.food_service.query.api.projection;

import com.foodie.food_service.commad.api.model.FoodRestModel;
import com.foodie.food_service.entity.Food;
import com.foodie.food_service.query.api.controller.FoodQueryController;
import com.foodie.food_service.query.api.queries.GetFoodQuery;
import com.foodie.food_service.respository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.axonframework.queryhandling.QueryHandler;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FoodProjection {       // here we Handle the Query and we can create the Event and send to the Data

    private final FoodRepository foodRepository;
    private final ModelMapper modelMapper;

    @QueryHandler
    public List<FoodRestModel> handle(GetFoodQuery foodQuery) {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
             .map(food -> modelMapper.map(food, FoodRestModel.class)).collect(Collectors.toList());
    }
}
