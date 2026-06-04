package com.qrorder.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentItemResponse {

    private String foodName;

    private Integer quantity;

    private Double unitPrice;

    private Double subtotal;
}
