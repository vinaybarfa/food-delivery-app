package com.foodie.food_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Food extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long price;
    @Column(length = 1000)
    @ElementCollection  // It will create a suppurate table for images
    private List<String> images;
    private Boolean isAvailable;
    private Long restaurantId;
    private Long orderItemId;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
    private Long ingredientsItemId;
    private Long cartItemId;
    private Long categoryId;
    private LocalDate date;
    //    @ManyToOne
//    private Category category;
//    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItem> orderItem = new ArrayList<>();
//    @ManyToOne
//    private Restaurant restaurant;
//    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<IngredientsItem> ingredientsItems = new ArrayList<>();
//    @OneToMany(mappedBy = "food")
//    private List<CartItem> cartItems;
}
