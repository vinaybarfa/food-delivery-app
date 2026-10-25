package com.sevice.order.request;

import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private Long userId;

//    private Address deliveryAddress;
}
