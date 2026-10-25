package com.vinay.category_service.service;

import com.vinay.category_service.dto.CategoryDTO;
import com.vinay.category_service.dto.RestaurantDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO, Long userId);

    List<CategoryDTO> findCategoryByRestaurantId(Long id) throws Exception;

    CategoryDTO findCategoryById(Long id) throws Exception;

    List<CategoryDTO> getAllCategory();
    
    CategoryDTO findCategoryByName(String name);

    List<RestaurantDTO> getAllRestaurantOfCategory(String categoryName);
}
