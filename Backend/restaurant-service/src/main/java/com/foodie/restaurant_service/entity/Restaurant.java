package com.foodie.restaurant_service.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "restaurant")

@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    private String rating;

    private String cuisineType;

    private String openingHours;

    private LocalTime openingTime;

    private LocalTime closingTime;

    @ElementCollection
    @CollectionTable(
            name = "restaurant_images",
            joinColumns = @JoinColumn(
                    name = "restaurant_id"
            )
    )
    @Column(name = "image")
    private List<String> images;

    private LocalDateTime registrationDate;

    private Boolean isOpen;

    private Long userId;
}
