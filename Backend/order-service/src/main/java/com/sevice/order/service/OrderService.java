package com.sevice.order.service;

import com.sevice.order.dto.OrderDTO;
import com.sevice.order.dto.UserDTO;
import com.sevice.order.request.OrderRequest;
import com.vinay.common_service.events.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderRequest orderRequest, UserDTO user);
    OrderDTO updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
    void cancelOrder(Long orderId) throws Exception;
    List<OrderDTO> getUsersOrder(Long userId) throws Exception;
    List<OrderDTO> getRestaurantsOrder(Long restaurantId, OrderStatus orderStatus) throws Exception;       // we can filter the order by status(DELIVERED, PENDING, ONGOING)
    OrderDTO findOrderById(Long orderId);
}
