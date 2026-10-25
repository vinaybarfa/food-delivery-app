package com.vinay.ingredient_service.service.serviceImpl;

import com.vinay.ingredient_service.dto.IngredientCategoryDTO;
import com.vinay.ingredient_service.dto.IngredientsItemDTO;
import com.vinay.ingredient_service.dto.RestaurantDTO;
import com.vinay.ingredient_service.entity.IngredientCategory;
import com.vinay.ingredient_service.entity.IngredientsItem;
import com.vinay.ingredient_service.repository.IngredientCategoryRepository;
import com.vinay.ingredient_service.repository.IngredientsItemRepository;
import com.vinay.ingredient_service.service.IngredientService;
import com.vinay.ingredient_service.service.external.CategoryService;
import com.vinay.ingredient_service.service.external.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    private final IngredientsItemRepository ingredientsItemRepository;

    @Override
    public IngredientCategoryDTO createIngredientCategory(String name, Long restaurantId) throws Exception {
        RestaurantDTO restaurantById = restaurantService.getRestaurantById(restaurantId);
        IngredientCategory ingredientCategory =
                IngredientCategory.builder()
                        .name(name)
                        .build();

        return modelMapper.map(ingredientCategoryRepository.save(ingredientCategory), IngredientCategoryDTO.class);
    }

    @Override
    public IngredientCategoryDTO findIngredientCategoryById(Long id) throws Exception {
        IngredientCategory ingredientCategory = ingredientCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("IngredientCategory not found"));
        return modelMapper.map(ingredientCategory, IngredientCategoryDTO.class);
    }

    @Override
    public List<IngredientCategoryDTO> findIngredientCategoryByRestaurantId(Long id) throws Exception {
//        Restaurant restaurant = restaurantService.findRestaurantById(id);
//        if (restaurant == null) {
//            throw new RuntimeException("Restaurant not found with id : " + id);
//        }
        List<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findByRestaurantId(id).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return ingredientCategory.stream().map(category -> modelMapper.map(category, IngredientCategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public IngredientsItemDTO createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        // Validate restaurant
        restaurantService.getRestaurantById(restaurantId);

        // Fetch model (not DTO)
        IngredientCategory category = ingredientCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        IngredientsItem item = IngredientsItem.builder()
                .name(ingredientName)
                .ingredientCategory(category)
                .build();

        IngredientsItem saved = ingredientsItemRepository.save(item);

        return modelMapper.map(saved, IngredientsItemDTO.class);
    }

    @Override
    public List<IngredientsItemDTO> findRestaurantIngredients(Long restaurantId){
        List<IngredientsItem> ingredientsItems = ingredientsItemRepository.findByRestaurantId(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return ingredientsItems.stream().map(category -> modelMapper.map(category, IngredientsItemDTO.class)).collect(Collectors.toList());

    }

    @Override
    public IngredientsItemDTO updateStock(Long id){
        IngredientsItem ingredientsItem = ingredientsItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingredients Item not found"));
        ingredientsItem.setIsStoke(!ingredientsItem.getIsStoke());
        IngredientsItem savedIngredientUItem = ingredientsItemRepository.save(ingredientsItem);
        return modelMapper.map(savedIngredientUItem, IngredientsItemDTO.class);
    }
}
