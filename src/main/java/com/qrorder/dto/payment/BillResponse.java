package com.qrorder.dto.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillResponse {

    private Long sessionId;

    private Double totalAmount;
}