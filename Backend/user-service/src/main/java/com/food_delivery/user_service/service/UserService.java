package com.food_delivery.user_service.service;


import com.food_delivery.user_service.dto.UserDTO;
import com.food_delivery.user_service.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;
    Optional<UserDTO> findUserByEmail(String email) throws Exception;
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDto);
    UserDTO updateUserById(UserDTO userDTO, Long userId);
    boolean deleteUserById(Long userId);
    UserDTO updatePartialUserById(Map<String, Object> update, Long userId);

    UserDTO findById(Long id);
}
