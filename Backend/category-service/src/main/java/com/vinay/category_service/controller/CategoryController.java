package com.vinay.category_service.controller;


import com.vinay.category_service.dto.CategoryDTO;
import com.vinay.category_service.dto.RestaurantDTO;
import com.vinay.category_service.service.CategoryService;
import com.vinay.category_service.service.external.RestaurantService;
import com.vinay.category_service.service.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @GetMapping("/admin/category/add")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category, @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(category, category.getId()));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findCategoryById(id));
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable("name") String categoryName, @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findCategoryByName(categoryName));
    }

    @GetMapping("/category/{name}/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantOfCategory(@PathVariable("name") String categoryName, @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllRestaurantOfCategory(categoryName));
    }

}
