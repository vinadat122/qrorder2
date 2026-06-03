package com.qrorder.dto.order;

import com.qrorder.entity.enums.OrderItemStatus;
import lombok.Data;

@Data
public class UpdateOrderItemStatusRequest {

    private OrderItemStatus status;
}
