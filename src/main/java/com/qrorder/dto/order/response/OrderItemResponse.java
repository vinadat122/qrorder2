package com.qrorder.dto.order.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private Long foodId;

    private String foodName;

    private Integer quantity;

    private String note;

    private String status;

    private Long itemId;

    private String type;
}