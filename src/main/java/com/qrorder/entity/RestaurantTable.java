package com.qrorder.entity;

import com.qrorder.entity.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableNumber;

    @Column(unique = true)
    private String qrToken;

    @Enumerated(EnumType.STRING)
    private TableStatus status;
}