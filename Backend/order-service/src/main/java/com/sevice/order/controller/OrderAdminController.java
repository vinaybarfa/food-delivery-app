package com.sevice.order.controller;

import com.sevice.order.dto.OrderDTO;
import com.sevice.order.service.OrderService;
import com.sevice.order.service.external.UserService;
import com.vinay.common_service.events.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class OrderAdminController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<OrderDTO>>
    getOrderHistory(

            @PathVariable Long id,

            @RequestParam(
                    required = false
            )
            OrderStatus orderStatus

    ) throws Exception {

        return ResponseEntity.ok(

                orderService.getRestaurantsOrder(
                        id,
                        orderStatus
                )
        );
    }

    @PutMapping(
            "/order/status/{id}/{orderStatus}"
    )
    public ResponseEntity<OrderDTO>
    updateOrderStatus(

            @PathVariable Long id,

            @PathVariable
            OrderStatus orderStatus

    ) throws Exception {

        return ResponseEntity.ok(

                orderService.updateOrderStatus(
                        id,
                        orderStatus
                )
        );
    }
}