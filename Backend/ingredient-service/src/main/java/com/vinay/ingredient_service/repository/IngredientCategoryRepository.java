package com.vinay.ingredient_service.repository;

import com.vinay.ingredient_service.entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

    Optional<List<IngredientCategory>> findByRestaurantId(Long restaurantId);
}