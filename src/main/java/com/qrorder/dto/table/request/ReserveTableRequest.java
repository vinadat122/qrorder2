package com.qrorder.dto.table.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReserveTableRequest {

    private String customerName;

    private String phone;

    private Integer guestCount;

    private LocalDateTime reservationTime;

    private String note;
}