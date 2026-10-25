package com.sevice.order.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemDTO {

    private Long id;
    private CartDTO cart;
    private Integer quantity;
    private Long totalPrice;
    private List<String> ingredients = new ArrayList<>();
    private Long foodId;
}
