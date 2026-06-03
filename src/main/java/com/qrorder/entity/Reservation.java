package com.qrorder.entity;


import com.qrorder.entity.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String customerName;

    private String phone;

    private Integer guestCount;

    private LocalDateTime reservationTime;

    private String note;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "table_id")

    private RestaurantTable table;
}

