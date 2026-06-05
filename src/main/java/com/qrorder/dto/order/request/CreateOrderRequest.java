package com.qrorder.dto.order.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull(message = "Session id is required")
    private Long sessionId;

    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<CreateOrderItemRequest> items;
}
