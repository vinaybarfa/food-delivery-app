package com.vinay.ingredient_service.service.external;

import com.vinay.ingredient_service.dto.CategoryDTO;
import com.vinay.ingredient_service.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "category-services", url = "http://localhost:1013")
public interface CategoryService {

    @PostMapping("/admin/category/add")
    CategoryDTO createCategory(@RequestBody CategoryDTO category);

    @GetMapping("/category/id/{id}")
    CategoryDTO getCategoryById(@PathVariable Long id);

    @GetMapping("/category/name/{name}")
    CategoryDTO getCategoryByName(@PathVariable String name);

    @GetMapping("/category/{name}/restaurants")
    List<RestaurantDTO> getAllRestaurantOfCategory(@PathVariable("name") String categoryName);

}


