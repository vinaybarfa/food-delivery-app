package com.foodie.food_service.query.api.controller;

import com.foodie.food_service.commad.api.model.FoodRestModel;
import com.foodie.food_service.query.api.queries.GetFoodQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodQueryController {          ///  'Query' means to fetch the Data, 'Command' means to give instruction for Data

    private QueryGateway queryGateway;

    @GetMapping("/foods")
    public List<FoodRestModel> getAllFood() {
        GetFoodQuery getFoodQuery = new GetFoodQuery();
        List<FoodRestModel> foodRestModels = queryGateway.query(getFoodQuery,
                ResponseTypes.multipleInstancesOf(FoodRestModel.class)).join();
        return foodRestModels;
    }
}
