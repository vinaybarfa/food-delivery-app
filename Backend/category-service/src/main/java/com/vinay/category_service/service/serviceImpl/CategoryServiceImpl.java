package com.vinay.category_service.service.serviceImpl;

import com.vinay.category_service.dto.CategoryDTO;
import com.vinay.category_service.dto.RestaurantDTO;
import com.vinay.category_service.dto.UserDTO;
import com.vinay.category_service.entity.Category;
import com.vinay.category_service.repository.CategoryRepository;
import com.vinay.category_service.service.CategoryService;
import com.vinay.category_service.service.external.RestaurantService;
import com.vinay.category_service.service.external.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO, Long userId) {
        UserDTO userById = userService.getUserById(userId);

        RestaurantDTO restaurant = restaurantService.findRestaurantByUserId(userById.getId());
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .restaurantId(restaurant.getId())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


    @Override
    public List<CategoryDTO> findCategoryByRestaurantId(Long id) throws Exception {
        RestaurantDTO restaurant = restaurantService.findRestaurantByUserId(id);
        List<Category> categories = categoryRepository.findByRestaurantId(restaurant.getId());

       return categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurantOfCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        RestaurantDTO restaurantById = restaurantService.getRestaurantById(category.getRestaurantId());
        return null;
    }
}
