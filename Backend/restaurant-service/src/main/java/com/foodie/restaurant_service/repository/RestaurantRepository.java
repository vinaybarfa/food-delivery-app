package com.foodie.restaurant_service.repository;

import com.foodie.restaurant_service.entity.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository
        extends JpaRepository<Restaurant, Long> {

    /*
     * FIND BY USER ID
     */
    Restaurant findByUserId(
            Long userId
    );

    /*
     * SEARCH RESTAURANT
     */
    @Query("""

            SELECT r

            FROM Restaurant r

            WHERE LOWER(r.name)

            LIKE LOWER(
                CONCAT('%', :keyword, '%')
            )

            OR LOWER(r.cuisineType)

            LIKE LOWER(
                CONCAT('%', :keyword, '%')
            )

            """)
    List<Restaurant> searchRestaurant(
            String keyword
    );
}
