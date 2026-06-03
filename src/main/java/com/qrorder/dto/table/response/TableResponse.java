package com.qrorder.dto.table.response;

import com.qrorder.entity.enums.TableStatus;

public class TableResponse {

    private Long id;

    private Integer tableNumber;

    private String qrToken;

    private TableStatus status;

    public TableResponse() {
    }

    public TableResponse(
            Long id,
            Integer tableNumber,
            String qrToken,
            TableStatus status
    ) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.qrToken = qrToken;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public String getQrToken() {
        return qrToken;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
