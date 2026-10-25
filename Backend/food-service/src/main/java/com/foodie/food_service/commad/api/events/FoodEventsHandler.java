package com.foodie.food_service.commad.api.events;

import com.foodie.food_service.entity.Food;
import com.foodie.food_service.respository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("food")
public class FoodEventsHandler {

    private final FoodRepository foodRepository;

    @EventHandler
    public void on(FoodCreatedEvent event) throws Exception {
        Food food = new Food();
        BeanUtils.copyProperties(event,food);
        foodRepository.save(food);
        throw new Exception("Expection Occurred");
    }

    @EventHandler
    public void handle(Exception exception) throws Exception {
        log.info("Exception Handled with message : {}",exception.getMessage());
        throw exception;
    }

}
