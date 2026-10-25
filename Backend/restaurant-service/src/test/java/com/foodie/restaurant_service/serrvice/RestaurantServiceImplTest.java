package com.foodie.restaurant_service.serrvice;

import com.foodie.restaurant_service.dto.CreateRestaurantRequestDTO;
import com.foodie.restaurant_service.dto.RestaurantDTO;
import com.foodie.restaurant_service.entity.Restaurant;
import com.foodie.restaurant_service.repository.RestaurantRepository;
import com.foodie.restaurant_service.service.serviceImpl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Restaurant restaurant;
    private RestaurantDTO restaurantDTO;

    @BeforeEach
    void setUp() {

        restaurant = Restaurant.builder()
                .id(1L)
                .name("Pizza Hut")
                .description("Best Pizza")
                .rating("4.5")
                .cuisineType("Italian")
                .isOpen(true)
                .userId(101L)
                .build();

        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(1L);
        restaurantDTO.setName("Pizza Hut");
    }

    @Test
    void testCreateRestaurant() throws Exception {

        CreateRestaurantRequestDTO request =
                new CreateRestaurantRequestDTO();

        request.setName("Pizza Hut");
        request.setDescription("Best Pizza");
        request.setRating("4.5");
        request.setCuisineType("Italian");

        when(restaurantRepository.save(any(Restaurant.class)))
                .thenReturn(restaurant);

        when(modelMapper.map(restaurant, RestaurantDTO.class))
                .thenReturn(restaurantDTO);

        RestaurantDTO result =
                restaurantService.createRestaurant(request);

        assertNotNull(result);
        assertEquals("Pizza Hut", result.getName());

        verify(restaurantRepository, times(1))
                .save(any(Restaurant.class));
    }

    @Test
    void testFindRestaurantById() throws Exception {

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(modelMapper.map(restaurant, RestaurantDTO.class))
                .thenReturn(restaurantDTO);

        RestaurantDTO result =
                restaurantService.findRestaurantById(1L);

        assertNotNull(result);
        assertEquals("Pizza Hut", result.getName());
    }

    @Test
    void testDeleteRestaurant() throws Exception {

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        restaurantService.deleteRestaurant(1L);

        verify(restaurantRepository).delete(restaurant);
    }

    @Test
    void testGetAllRestaurant() {

        List<Restaurant> restaurants =
                Collections.singletonList(restaurant);

        when(restaurantRepository.findAll())
                .thenReturn(restaurants);

        when(modelMapper.map(restaurant, RestaurantDTO.class))
                .thenReturn(restaurantDTO);

        List<RestaurantDTO> result =
                restaurantService.getAllRestaurant();

        assertEquals(1, result.size());
        assertEquals("Pizza Hut",
                result.get(0).getName());
    }

    @Test
    void testFindRestaurantByUserId() throws Exception {

        when(restaurantRepository.findByUserId(101L))
                .thenReturn(restaurant);

        when(modelMapper.map(restaurant, RestaurantDTO.class))
                .thenReturn(restaurantDTO);

        RestaurantDTO result =
                restaurantService.findRestaurantByUserId(101L);

        assertNotNull(result);
        assertEquals("Pizza Hut", result.getName());
    }

    @Test
    void testSearchRestaurant() throws Exception {

        List<Restaurant> restaurants =
                Collections.singletonList(restaurant);

        when(restaurantRepository.searchRestaurant("Pizza"))
                .thenReturn(restaurants);

        when(modelMapper.map(restaurant, RestaurantDTO.class))
                .thenReturn(restaurantDTO);

        List<RestaurantDTO> result =
                restaurantService.searchRestaurant("Pizza");

        assertEquals(1, result.size());
        assertEquals("Pizza Hut",
                result.get(0).getName());
    }

    @Test
    void testUpdateRestaurantStatus() throws Exception {

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(restaurantRepository.save(any(Restaurant.class)))
                .thenReturn(restaurant);

        when(modelMapper.map(any(Restaurant.class),
                eq(RestaurantDTO.class)))
                .thenReturn(restaurantDTO);

        RestaurantDTO result =
                restaurantService.updateRestaurantStatus(1L);

        assertNotNull(result);

        verify(restaurantRepository)
                .save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant() throws Exception {

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Dominos");
        updates.put("rating", "5.0");

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(restaurantRepository.save(any(Restaurant.class)))
                .thenReturn(restaurant);

        when(modelMapper.map(any(Restaurant.class),
                eq(RestaurantDTO.class)))
                .thenReturn(restaurantDTO);

        RestaurantDTO result =
                restaurantService.updateRestaurant(1L, updates);

        assertNotNull(result);

        verify(restaurantRepository)
                .save(any(Restaurant.class));
    }
}