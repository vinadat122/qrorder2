package com.qrorder.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")

@Getter
@Setter

@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Payment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "session_id",
            nullable = false,
            unique = true
    )
    private TableSession session;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime paidAt;
}