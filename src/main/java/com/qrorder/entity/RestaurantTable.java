package com.qrorder.entity;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private Integer tableNumber;

    private Integer capacity;

    @Column(unique = true)
    private String qrToken;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    private LocalDateTime reservedAt;

    private String reservationName;

    private String reservationPhone;
}