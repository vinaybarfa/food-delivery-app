package com.vinay.cart_service.request;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long cartItemId;
    private Integer quantity;
}
