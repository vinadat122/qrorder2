package com.qrorder.entity;

import com.qrorder.entity.enums.FoodType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FoodType type;

    private Double price;

    private String description;

    private String image;

    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}