package com.qrorder.dto.reservation;

import com.qrorder.entity.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private Long id;

    private String customerName;

    private String phone;

    private Integer guestCount;

    private LocalDateTime reservationTime;

    private String note;

    private ReservationStatus status;

    private Long tableId;

    private Integer tableNumber;
}