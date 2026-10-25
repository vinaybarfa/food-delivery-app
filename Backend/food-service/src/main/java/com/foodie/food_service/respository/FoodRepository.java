package com.foodie.food_service.respository;

import com.foodie.food_service.entity.Food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository
        extends JpaRepository<Food, Long> {

    @Query(
            value = """
                SELECT *
                FROM food
                WHERE LOWER(name)
                LIKE LOWER(CONCAT('%', :keyword, '%'))
                """,
            nativeQuery = true
    )
    List<Food> searchFood(
            @Param("keyword")
            String keyword
    );
}