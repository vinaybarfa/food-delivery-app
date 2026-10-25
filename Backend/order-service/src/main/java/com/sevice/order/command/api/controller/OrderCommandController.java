package com.sevice.order.command.api.controller;

import com.sevice.order.command.api.command.CreateOrderCommand;
import com.sevice.order.command.api.model.OrderRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderCommandController {       // this command controller will Handle the Post request for creating the Order

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderRestModel orderRestModel) {
        String orderId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand = CreateOrderCommand
                .builder()
                .orderId(orderId)
                .addressId(orderRestModel.getAddressId())
                .userId(orderRestModel.getUserId())
                .foodId(orderRestModel.getFoodId())
                .quantity(orderRestModel.getQuantity())
                .orderStatus("CREATED")
                .build();

        commandGateway.sendAndWait(createOrderCommand);
        return "order created";
    }
}
