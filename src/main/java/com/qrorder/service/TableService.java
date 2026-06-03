package com.qrorder.service;

import com.qrorder.dto.table.request.CreateTableRequest;
import com.qrorder.dto.table.request.ReserveTableRequest;
import com.qrorder.dto.table.response.TableResponse;

import com.qrorder.entity.RestaurantTable;

import java.util.List;

public interface TableService {

    void createTable(CreateTableRequest request);

    void reserveTable(
            Long tableId,
            ReserveTableRequest request
    );

    Long checkIn(Long tableId);

    List<TableResponse> getTables();

    void resetTable(Long tableId);
}