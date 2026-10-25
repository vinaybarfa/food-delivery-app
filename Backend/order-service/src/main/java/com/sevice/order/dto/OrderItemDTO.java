package com.sevice.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemDTO {
    private Long id;
    private Double discount;
    private Double totalPrice;
    private Integer quantity;
    private List<String> ingredients;
}
