package com.qrorder.entity;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(unique = true, nullable = false)
    private Integer tableNumber;

    @Column(unique = true)
    private String qrToken;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    @OneToMany(mappedBy = "table")
    private List<TableSession> sessions;

    @OneToMany(mappedBy = "table")
    private List<Reservation> reservations;

    private Integer capacity;
}