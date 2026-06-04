package com.qrorder.dto.order.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private Long sessionId;

    private List<CreateOrderItemRequest> items;
}