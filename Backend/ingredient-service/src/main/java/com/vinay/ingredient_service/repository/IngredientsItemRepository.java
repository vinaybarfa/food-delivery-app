package com.vinay.ingredient_service.repository;

import com.vinay.ingredient_service.entity.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientsItemRepository extends JpaRepository<IngredientsItem, Long> {

    Optional<List<IngredientsItem>> findByRestaurantId(Long restaurantId);
}