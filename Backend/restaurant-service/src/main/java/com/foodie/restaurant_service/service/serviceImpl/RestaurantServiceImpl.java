package com.foodie.restaurant_service.service.serviceImpl;

import com.foodie.restaurant_service.dto.CreateRestaurantRequestDTO;
import com.foodie.restaurant_service.dto.RestaurantDTO;
import com.foodie.restaurant_service.entity.Restaurant;
import com.foodie.restaurant_service.repository.RestaurantRepository;
import com.foodie.restaurant_service.service.RestaurantService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl
        implements RestaurantService {

    private final RestaurantRepository
            restaurantRepository;

    private final ModelMapper modelMapper;

    @Override
    public RestaurantDTO createRestaurant(
            CreateRestaurantRequestDTO request
    ) throws Exception {

        Restaurant restaurant =
                Restaurant.builder()

                        .name(request.getName())

                        .description(
                                request.getDescription()
                        )

                        .rating(
                                request.getRating()
                        )

                        .cuisineType(
                                request.getCuisineType()
                        )

                        .openingHours(
                                request.getOpeningHours()
                        )

                        .openingTime(
                                request.getOpeningTime()
                        )

                        .closingTime(
                                request.getClosingTime()
                        )

                        .images(
                                request.getImages()
                        )

                        .isOpen(true)

                        .userId(
                                request.getUserId()
                        )

                        .registrationDate(
                                LocalDateTime.now()
                        )

                        .build();

        Restaurant savedRestaurant =
                restaurantRepository.save(
                        restaurant
                );

        return modelMapper.map(
                savedRestaurant,
                RestaurantDTO.class
        );
    }

    @Override
    public RestaurantDTO updateRestaurant(

            Long restaurantId,

            Map<String, Object> updates

    ) throws Exception {

        Restaurant restaurant =
                restaurantRepository

                        .findById(restaurantId)

                        .orElseThrow(() ->
                                new Exception(
                                        "Restaurant Not Found"
                                )
                        );

        if (updates.containsKey("name")) {

            restaurant.setName(
                    updates.get("name").toString()
            );
        }

        if (updates.containsKey("description")) {

            restaurant.setDescription(
                    updates.get("description").toString()
            );
        }

        if (updates.containsKey("rating")) {

            restaurant.setRating(
                    updates.get("rating").toString()
            );
        }

        if (updates.containsKey("cuisineType")) {

            restaurant.setCuisineType(
                    updates.get("cuisineType").toString()
            );
        }

        Restaurant updatedRestaurant =
                restaurantRepository.save(
                        restaurant
                );

        return modelMapper.map(
                updatedRestaurant,
                RestaurantDTO.class
        );
    }

    @Override
    public void deleteRestaurant(
            Long restaurantId
    ) throws Exception {

        Restaurant restaurant =
                restaurantRepository

                        .findById(restaurantId)

                        .orElseThrow(() ->
                                new Exception(
                                        "Restaurant Not Found"
                                )
                        );

        restaurantRepository.delete(
                restaurant
        );
    }

    @Override
    public List<RestaurantDTO> getAllRestaurant() {

        List<Restaurant> restaurants =
                restaurantRepository.findAll();

        return restaurants.stream()

                .map(restaurant ->
                        modelMapper.map(
                                restaurant,
                                RestaurantDTO.class
                        )
                )

                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO findRestaurantById(
            Long restaurantId
    ) throws Exception {

        Restaurant restaurant =
                restaurantRepository

                        .findById(restaurantId)

                        .orElseThrow(() ->
                                new Exception(
                                        "Restaurant Not Found"
                                )
                        );

        return modelMapper.map(
                restaurant,
                RestaurantDTO.class
        );
    }

    @Override
    public RestaurantDTO findRestaurantByUserId(
            Long userId
    ) throws Exception {

        Restaurant restaurant =
                restaurantRepository
                        .findByUserId(userId);

        if (restaurant == null) {

            throw new Exception(
                    "Restaurant Not Found"
            );
        }

        return modelMapper.map(
                restaurant,
                RestaurantDTO.class
        );
    }

    @Override
    public List<RestaurantDTO> searchRestaurant(
            String keyword
    ) throws Exception {

        List<Restaurant> restaurants =
                restaurantRepository
                        .searchRestaurant(keyword);

        return restaurants.stream()

                .map(restaurant ->
                        modelMapper.map(
                                restaurant,
                                RestaurantDTO.class
                        )
                )

                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO updateRestaurantStatus(
            Long restaurantId
    ) throws Exception {

        Restaurant restaurant =
                restaurantRepository

                        .findById(restaurantId)

                        .orElseThrow(() ->
                                new Exception(
                                        "Restaurant Not Found"
                                )
                        );

        restaurant.setIsOpen(
                !restaurant.getIsOpen()
        );

        Restaurant updatedRestaurant =
                restaurantRepository.save(
                        restaurant
                );

        return modelMapper.map(
                updatedRestaurant,
                RestaurantDTO.class
        );
    }
}
