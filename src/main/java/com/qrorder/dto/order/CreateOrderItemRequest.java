package com.qrorder.dto.order;

import lombok.Data;

@Data
public class CreateOrderItemRequest {

    private Long foodId;

    private Integer quantity;

    private String note;
}