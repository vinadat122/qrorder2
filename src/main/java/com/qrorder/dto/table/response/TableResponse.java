package com.qrorder.dto.table.response;

import com.qrorder.entity.enums.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableResponse {

    private Long id;

    private Integer tableNumber;

    private Integer capacity;

    private String qrToken;

    private TableStatus status;
}