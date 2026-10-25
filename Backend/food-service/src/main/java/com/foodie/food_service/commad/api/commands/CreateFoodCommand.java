package com.foodie.food_service.commad.api.commands;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CreateFoodCommand {       // command is like a DTO

    @TargetAggregateIdentifier      // it will define the uniqueness
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
}
