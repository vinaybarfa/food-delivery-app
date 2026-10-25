package com.sevice.order.service.serviceImpl;

import com.sevice.order.dto.*;
import com.sevice.order.entity.Order;
import com.sevice.order.entity.OrderItem;
import com.sevice.order.event.OrderTrackingEvent;
import com.sevice.order.repository.OrderItemRepository;
import com.sevice.order.repository.OrderRepository;
import com.sevice.order.request.OrderRequest;
import com.sevice.order.service.OrderService;
import com.sevice.order.service.external.CartService;
import com.sevice.order.service.external.RestaurantService;
import com.sevice.order.service.external.UserService;
import com.vinay.common_service.events.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public OrderDTO createOrder(
            OrderRequest orderRequest,
            UserDTO user
    ) {

        RestaurantDTO restaurant =
                restaurantService.getRestaurantById(
                        orderRequest.getRestaurantId()
                );

        if (restaurant == null) {
            throw new RuntimeException(
                    "Restaurant Not Found"
            );
        }

        CartDTO cart =
                cartService.getUserCart(
                        user.getId()
                );

        if (
                cart == null ||
                        cart.getItems() == null ||
                        cart.getItems().isEmpty()
        ) {
            throw new RuntimeException(
                    "Cart is Empty"
            );
        }

        Order order = Order.builder()

                .customerId(
                        user.getId()
                )

                .restaurantId(
                        restaurant.getId()
                )

                .createdAt(
                        java.time.LocalDateTime.now()
                )

                .orderStatus(
                        com.vinay.common_service.events.OrderStatus.PENDING
                )

                .totalAmount(
                        cart.getTotal()
                )

                .totalPrice(
                        cart.getTotal().intValue()
                )

                .totalItem(
                        cart.getItems().size()
                )

                .build();

        Order savedOrder =
                orderRepository.save(order);

        List<OrderItem> orderItems =
                new ArrayList<>();

        Long totalQuantity = 0L;

        for (CartItemDTO cartItem : cart.getItems()) {

            OrderItem orderItem =
                    OrderItem.builder()

                            .quantity(
                                    cartItem.getQuantity()
                            )

                            .totalPrice(
                                    cartItem.getTotalPrice()
                                            .doubleValue()
                            )

                            .discount(0.0)

                            .ingredients(
                                    cartItem.getIngredients()
                            )

                            .order(savedOrder)

                            .build();

            totalQuantity +=
                    cartItem.getQuantity();

            orderItems.add(
                    orderItemRepository.save(
                            orderItem
                    )
            );
        }

        savedOrder.setItems(
                orderItems
        );

        savedOrder.setQuantity(
                totalQuantity
        );

        savedOrder =
                orderRepository.save(
                        savedOrder
                );

        return modelMapper.map(
                savedOrder,
                OrderDTO.class
        );
    }


    @Override
    public OrderDTO updateOrderStatus(
            Long orderId,
            OrderStatus orderStatus
    ) throws Exception {

        Order order =
                orderRepository.findById(
                                orderId
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order Not Found"
                                )
                        );

        order.setOrderStatus(
                orderStatus
        );

        Order savedOrder =
                orderRepository.save(
                        order
                );

        OrderTrackingEvent event =

                OrderTrackingEvent
                        .builder()

                        .orderId(
                                savedOrder.getOrderId()
                        )

                        .customerId(
                                savedOrder.getCustomerId()
                        )

                        .orderStatus(
                                savedOrder.getOrderStatus()
                        )

                        .timestamp(
                                LocalDateTime.now()
                        )

                        .build();

        messagingTemplate.convertAndSend(

                "/topic/order/" +
                        savedOrder.getCustomerId(),

                event
        );

        return modelMapper.map(

                savedOrder,

                OrderDTO.class
        );
    }

    @Override
    public void cancelOrder(
            Long orderId
    ) throws Exception {

        Order order =
                orderRepository.findById(orderId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order Not Found"
                                )
                        );

        orderRepository.delete(
                order
        );
    }

    @Override
    public List<OrderDTO> getUsersOrder(
            Long userId
    ) throws Exception {

        List<Order> orders =
                orderRepository.findByCustomerId(
                        userId
                );

        return orders.stream()

                .map(order ->
                        modelMapper.map(
                                order,
                                OrderDTO.class
                        )
                )

                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getRestaurantsOrder(
            Long restaurantId,
            OrderStatus orderStatus
    ) throws Exception {

        List<Order> orders =
                orderRepository.findByRestaurantId(
                        restaurantId
                );

        if (orderStatus != null) {

            orders = orders.stream()

                    .filter(order ->
                            order.getOrderStatus()
                                    .equals(orderStatus)
                    )

                    .collect(Collectors.toList());
        }

        return orders.stream()

                .map(order ->
                        modelMapper.map(
                                order,
                                OrderDTO.class
                        )
                )

                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findOrderById(
            Long orderId
    ) {

        Order order =
                orderRepository.findById(orderId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order Not Found : "
                                                + orderId
                                )
                        );

        return modelMapper.map(
                order,
                OrderDTO.class
        );
    }
}
