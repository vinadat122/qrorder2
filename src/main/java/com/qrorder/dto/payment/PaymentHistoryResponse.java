package com.qrorder.dto.payment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentHistoryResponse {

    private Long paymentId;

    private Long sessionId;

    private Long tableId;

    private Integer tableNumber;

    private Double amount;

    private LocalDateTime paidAt;
}