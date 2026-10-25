package com.vinay.cart_service.dto;

import com.vinay.cart_service.entity.Cart;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemDTO {

    private Long id;
    private Cart cart;
    private Integer quantity;
    private Long totalPrice;
    private List<String> ingredients = new ArrayList<>();
    private Long foodId;
}
