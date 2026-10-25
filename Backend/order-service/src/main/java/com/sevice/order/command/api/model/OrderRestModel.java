package com.sevice.order.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRestModel {

    private String foodId;
    private String userId;
    private String addressId;
    private Integer quantity;

}
