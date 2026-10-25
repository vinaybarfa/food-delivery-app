package com.food_delivery.user_service.dto;

import com.food_delivery.user_service.entity.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private boolean isAvailable = true;
//    private List<Order> orders = new ArrayList<>();
//    private List<Address> addresses = new ArrayList<>();

}
