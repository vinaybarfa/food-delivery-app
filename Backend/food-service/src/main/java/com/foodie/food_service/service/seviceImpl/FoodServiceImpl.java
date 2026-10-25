package com.foodie.food_service.service.seviceImpl;

import com.foodie.food_service.dto.CategoryDTO;
import com.foodie.food_service.dto.FoodDTO;
import com.foodie.food_service.dto.RestaurantDTO;
import com.foodie.food_service.entity.Food;
import com.foodie.food_service.request.CreateFoodRequest;
import com.foodie.food_service.respository.FoodRepository;
import com.foodie.food_service.service.FoodService;
import com.foodie.food_service.service.external.CategoryService;
import com.foodie.food_service.service.external.RestaurantService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    private final RestTemplate restTemplate;

    private final ModelMapper modelMapper;

    private final RestaurantService restaurantService;

    private final CategoryService categoryService;

    @Override
    public FoodDTO createFood(
            CreateFoodRequest createFoodRequest,
            CategoryDTO category,
            RestaurantDTO restaurant
    ) {

        Food food = Food.builder()

                .name(
                        createFoodRequest.getName()
                )

                .description(
                        createFoodRequest.getDescription()
                )

                .price(
                        createFoodRequest.getPrice()
                )

                .images(
                        createFoodRequest.getImages()
                )

                .restaurantId(
                        restaurant.getId()
                )

                .isVegetarian(
                        createFoodRequest.isVegetarian()
                )

                .isSeasonal(
                        createFoodRequest.isSeasonal()
                )

                .isAvailable(
                        createFoodRequest.getIsAvailable()
                )

                .ingredientsItemId(
                        createFoodRequest.getIngredientItemId()
                )

                .categoryId(
                        createFoodRequest.getCategoryId()
                )

                .date(
                        LocalDate.now()
                )

                .build();

        Food savedFood =
                foodRepository.save(
                        food
                );

        return modelMapper.map(
                savedFood,
                FoodDTO.class
        );
    }

    @Override
    public List<FoodDTO> getRestaurantsFood(
            String token,
            Long restaurantId,
            boolean isVegetarian,
            boolean isNonVegetarian,
            boolean isSeasonal,
            String foodCategory
    ) {

        RestaurantDTO restaurant =

                restaurantService
                        .getRestaurantById(
                                token,
                                restaurantId
                        );

        List<FoodDTO> foods =
                restaurant.getFoodDTO();

        if (isVegetarian) {

            foods =
                    filterByVegetarian(
                            foods
                    );
        }

        if (isNonVegetarian) {

            foods =
                    filterByNonVeg(
                            foods
                    );
        }

        if (isSeasonal) {

            foods =
                    filterBySeasonal(
                            foods
                    );
        }

        if (
                foodCategory != null &&
                        !foodCategory.isBlank()
        ) {

            foods =
                    filterByCategory(
                            foods,
                            foodCategory
                    );
        }

        return foods;
    }

    private List<FoodDTO> filterByCategory(
            List<FoodDTO> foods,
            String foodCategory
    ) {

        return foods.stream()

                .filter(food -> {

                    CategoryDTO category =

                            categoryService
                                    .getCategoryById(
                                            food.getCategoryId()
                                    );

                    return category != null &&
                            category.getName()
                                    .equalsIgnoreCase(
                                            foodCategory
                                    );
                })

                .collect(Collectors.toList());
    }

    private List<FoodDTO> filterByVegetarian(
            List<FoodDTO> foods
    ) {

        return foods.stream()

                .filter(
                        FoodDTO::getIsVegetarian
                )

                .collect(
                        Collectors.toList()
                );
    }

    private List<FoodDTO> filterByNonVeg(
            List<FoodDTO> foods
    ) {

        return foods.stream()

                .filter(
                        food ->
                                !food.getIsVegetarian()
                )

                .collect(
                        Collectors.toList()
                );
    }

    private List<FoodDTO> filterBySeasonal(
            List<FoodDTO> foods
    ) {

        return foods.stream()

                .filter(
                        FoodDTO::getIsSeasonal
                )

                .collect(
                        Collectors.toList()
                );
    }

    @Override
    public FoodDTO findByFoodId(
            Long id
    ) throws Exception {

        Food food =

                foodRepository.findById(id)

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Food Not Found"
                                        )
                        );

        String restaurantServiceUrl =

                "http://localhost:1011/api/restaurant/"
                        + food.getRestaurantId();

        RestaurantDTO restaurantDTO =

                restTemplate.getForObject(
                        restaurantServiceUrl,
                        RestaurantDTO.class
                );

        FoodDTO foodDTO =

                modelMapper.map(
                        food,
                        FoodDTO.class
                );

        return foodDTO;
    }

    @Override
    public void deleteFood(
            Long id
    ) throws Exception {

        findByFoodId(id);

        foodRepository.deleteById(id);
    }

    @Override
    public List<FoodDTO> searchFood(
            String keyword
    ) {

        List<Food> foods =

                foodRepository.searchFood(
                        keyword
                );

        return foods.stream()

                .map(
                        food ->
                                modelMapper.map(
                                        food,
                                        FoodDTO.class
                                )
                )

                .collect(
                        Collectors.toList()
                );
    }

    @Override
    public FoodDTO updateAvailabilityStatus(
            Long foodId
    ) throws Exception {

        Food food =

                foodRepository.findById(
                                foodId
                        )

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Food Not Found"
                                        )
                        );

        food.setIsAvailable(
                !food.getIsAvailable()
        );

        Food savedFood =
                foodRepository.save(
                        food
                );

        return modelMapper.map(
                savedFood,
                FoodDTO.class
        );
    }

    @Override
    public List<FoodDTO> getAllFoods() {

        return foodRepository
                .findAll()
                .stream()
                .map(food ->
                        modelMapper.map(
                                food,
                                FoodDTO.class
                        )
                )
                .toList();
    }


}
