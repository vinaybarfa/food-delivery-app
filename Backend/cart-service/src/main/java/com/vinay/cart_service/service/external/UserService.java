package com.vinay.cart_service.service.external;

import com.vinay.cart_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-services", url = "http://localhost:1010")
public interface UserService {

    @GetMapping("/api/users/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);     // We only have to Declare

    @GetMapping("/api/users/view-all-users")
    List<UserDTO> getAllUser();

}
