package com.vinay.cart_service.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {

    private Long foodId;
    private Integer quantity;
    private List<String> ingredients;
    private Long userId;
}
