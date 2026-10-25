package com.foodie.restaurant_service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String password;
//    private Role role;
    private boolean isAvailable = true;
    private List<RestaurantDTO> favorites = new ArrayList<>();
    private Long orderId;
    private Long addressId;
//    private List<Order> orders = new ArrayList<>();
//    private List<Address> addresses = new ArrayList<>();

}
