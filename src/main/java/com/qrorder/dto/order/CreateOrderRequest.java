package com.qrorder.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private Long sessionId;

    private List<CreateOrderItemRequest> items;
}