package com.qrorder.dto.order.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateOrderItemRequest {

    @NotNull(message = "Food id is required")
    private Long foodId;

    @NotNull(message = "Quantity is required")
    @Min(
            value = 1,
            message = "Quantity must be at least 1"
    )
    private Integer quantity;

    private String note;
}