package com.vinay.cart_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CartDTO {

    private Long id;
    private Double amount;
    private Long total;
    private LocalDate expiryDate;
    private List<CartItemDTO> items;

}
