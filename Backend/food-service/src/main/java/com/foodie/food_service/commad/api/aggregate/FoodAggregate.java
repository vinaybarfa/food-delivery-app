package com.foodie.food_service.commad.api.aggregate;

import com.foodie.food_service.commad.api.commands.CreateFoodCommand;
import com.foodie.food_service.commad.api.events.FoodCreatedEvent;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Aggregate
public class FoodAggregate {        // this Aggregate will Handle the Command from createFoodCommand

    @AggregateIdentifier
    private Long foodId;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(length = 1000)
    @ElementCollection  // It will create a suppurate table for images
    private List<String> images;
    private Boolean isAvailable;
    private Long restaurantId;
    private Long orderItemId;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
    private Long ingredientsItemId;
    private Long cartItemId;
    private Long categoryId;
    private LocalDate date;

    @CommandHandler
    public FoodAggregate(CreateFoodCommand createFoodCommand) {
        // You can perform all the validations
        FoodCreatedEvent foodCreatedEvent = new FoodCreatedEvent();

        BeanUtils.copyProperties(createFoodCommand, foodCreatedEvent);      // here we pass all the properties of CreateFoodCommand to FoodCreatedEvent

        AggregateLifecycle.apply(foodCreatedEvent); // here we publish the event
    }

    public FoodAggregate() {

    }

    @EventSourcingHandler       // when every we handle the command then you should handle the state
    public void on(FoodCreatedEvent foodCreatedEvent) {
        this.name = foodCreatedEvent.getName();
        this.date = foodCreatedEvent.getDate();
        this.foodId = foodCreatedEvent.getFoodId();
        this.price = foodCreatedEvent.getPrice();
        this.description = foodCreatedEvent.getDescription();
        this.images = foodCreatedEvent.getImages();
        this.cartItemId = foodCreatedEvent.getCartItemId();
        this.ingredientsItemId = foodCreatedEvent.getIngredientsItemId();
        this.isSeasonal = foodCreatedEvent.getIsSeasonal();
        this.isAvailable = foodCreatedEvent.getIsAvailable();
        this.isVegetarian = foodCreatedEvent.getIsVegetarian();
        this.restaurantId = foodCreatedEvent.getRestaurantId();
        this.orderItemId = foodCreatedEvent.getOrderItemId();
    }
}
