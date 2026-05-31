package com.qrorder.dto.table.request;

import lombok.Data;

@Data
public class CreateTableRequest {

    private Integer tableNumber;

    private Integer capacity;
}