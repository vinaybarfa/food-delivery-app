package com.vinay.ingredient_service.controller;

import com.vinay.ingredient_service.dto.IngredientCategoryDTO;
import com.vinay.ingredient_service.dto.IngredientsItemDTO;
import com.vinay.ingredient_service.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    // ------------------ CATEGORY APIs ------------------

    @PostMapping("/category")
    public ResponseEntity<IngredientCategoryDTO> createCategory(
            @RequestParam @NotBlank String name,
            @RequestParam @NotNull Long restaurantId
    ) throws Exception {

        IngredientCategoryDTO category =
                ingredientService.createIngredientCategory(name, restaurantId);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<IngredientCategoryDTO> getCategoryById(
            @PathVariable Long id
    ) throws Exception {

        return ResponseEntity.ok(
                ingredientService.findIngredientCategoryById(id)
        );
    }

    @GetMapping("/category/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientCategoryDTO>> getCategoriesByRestaurant(
            @PathVariable Long restaurantId
    ) throws Exception {

        return ResponseEntity.ok(
                ingredientService.findIngredientCategoryByRestaurantId(restaurantId)
        );
    }

    // ------------------ INGREDIENT ITEM APIs ------------------

    @PostMapping("/item")
    public ResponseEntity<IngredientsItemDTO> createIngredientItem(
            @RequestParam Long restaurantId,
            @RequestParam String name,
            @RequestParam Long categoryId
    ) throws Exception {

        IngredientsItemDTO item = ingredientService.createIngredientsItem(
                restaurantId, name, categoryId
        );

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @GetMapping("/item/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientsItemDTO>> getRestaurantIngredients(
            @PathVariable Long restaurantId
    ) {

        return ResponseEntity.ok(
                ingredientService.findRestaurantIngredients(restaurantId)
        );
    }

    @PatchMapping("/item/{id}/stock")
    public ResponseEntity<IngredientsItemDTO> updateStock(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ingredientService.updateStock(id)
        );
    }
}