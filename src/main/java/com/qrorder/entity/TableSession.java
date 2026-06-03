package com.qrorder.entity;

import com.qrorder.entity.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.qrorder.entity.enums.SessionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany( mappedBy = "session",
            cascade = CascadeType.ALL
    )
    private List<Order> orders;

    @Column(unique = true)
    private String sessionToken;

    private String customerName;

    private String customerPhone;

    private String note;

}