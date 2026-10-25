package com.vinay.ingredient_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long restaurantId;

    @OneToMany(
            mappedBy = "ingredientCategory",
            cascade = CascadeType.ALL
    )
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();

}
