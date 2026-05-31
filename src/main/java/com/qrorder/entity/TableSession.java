package com.qrorder.entity;

import com.qrorder.entity.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.qrorder.entity.enums.SessionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "table_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableSession {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @Column(unique = true)
    private String sessionToken;

    private String customerName;

    private String customerPhone;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    private BigDecimal subtotal;

    private BigDecimal serviceCharge;

    private BigDecimal taxAmount;

    private BigDecimal discountAmount;

    private BigDecimal finalAmount;

    private String note;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}