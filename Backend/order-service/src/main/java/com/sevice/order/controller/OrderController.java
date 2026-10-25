package com.sevice.order.controller;

import com.sevice.order.dto.OrderDTO;
import com.sevice.order.dto.UserDTO;
import com.sevice.order.request.OrderRequest;
import com.sevice.order.service.OrderService;
import com.sevice.order.service.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/order")
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody OrderRequest orderRequest
    ) {

        UserDTO user =
                userService.getUserById(
                        orderRequest.getUserId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        orderService.createOrder(
                                orderRequest,
                                user
                        )
                );
    }

    @GetMapping("/order/user/{userId}")
    public ResponseEntity<List<OrderDTO>>
    getOrderHistory(
            @PathVariable Long userId
    ) throws Exception {

        return ResponseEntity.ok(
                orderService.getUsersOrder(
                        userId
                )
        );
    }
}