package com.sevice.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_items")

@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    /*
     * FOOD INFO SNAPSHOT
     */

    private Long foodId;

    private String foodName;

    private String foodImage;

    /*
     * PRICE
     */

    private Double price;

    private Double discount;

    private Double totalPrice;

    private Integer quantity;

    @ElementCollection
    private List<String> ingredients =
            new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_id")
    private Order order;
}
