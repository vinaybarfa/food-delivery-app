package com.vinay.common.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public record OrderItemDto(Long itemId, String productId, int quantity, BigDecimal productPrice) implements Serializable {

    @Serial private static final long serialValueUID = 1L;

    public BigDecimal getPrice() {
        return this.productPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

}
