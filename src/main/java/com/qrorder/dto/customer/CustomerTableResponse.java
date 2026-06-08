package com.qrorder.dto.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerTableResponse {

    private Long tableId;

    private Integer tableNumber;

    private String sessionToken;

    private Long sessionId;
}

