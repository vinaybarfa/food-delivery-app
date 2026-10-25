package com.food_delivery.user_service.controller;

import com.food_delivery.user_service.dto.UserDTO;
import com.food_delivery.user_service.entity.User;
import com.food_delivery.user_service.exception.ResourceNotFoundException;
import com.food_delivery.user_service.security.CustomUserService;
import com.food_delivery.user_service.service.UserService;
import com.vinay.common_service.quries.GetUserPaymentDetailsQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final transient QueryGateway queryGateway;
    private final ModelMapper modelMapper;

    @GetMapping("/viewUser")
    public ResponseEntity<UserDTO> getUserByEmail(
            @RequestParam("email") String email
    ) throws Exception {

        Optional<UserDTO> userDTO = userService.findUserByEmail(email);

        return userDTO
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("userId") Long userId
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(userId));
    }

    @GetMapping("/view-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @AuthenticationPrincipal CustomUserService userDetails
    ) {

        User user = userDetails.getUser();

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRole(user.getRole());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/new-user")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody @Valid UserDTO inputUser
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(inputUser));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserById(
            @RequestBody @Valid UserDTO userDTO,
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                userService.updateUserById(userDTO, userId)
        );
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updatePartialUserById(
            @RequestBody Map<String, Object> update,
            @PathVariable Long userId
    ) {

        UserDTO userDTO =
                userService.updatePartialUserById(update, userId);

        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUserById(
            @PathVariable Long userId
    ) {

        boolean gotDeleted = userService.deleteUserById(userId);

        if (gotDeleted) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/payment-details")
    public User getUserPaymentDetails(
            @PathVariable Long userId
    ) {

        GetUserPaymentDetailsQuery query =
                new GetUserPaymentDetailsQuery(userId);

        return queryGateway.query(
                query,
                ResponseTypes.instanceOf(User.class)
        ).join();
    }
}