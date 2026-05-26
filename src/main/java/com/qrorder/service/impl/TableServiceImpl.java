package com.qrorder.service.impl;

import com.qrorder.dto.table.CreateTableRequest;
import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.enums.TableStatus;
import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final RestaurantTableRepository tableRepository;

    @Override
    public void createTable(CreateTableRequest request) {

        RestaurantTable table = RestaurantTable.builder()
                .tableNumber(request.getTableNumber())
                .qrToken(UUID.randomUUID().toString())
                .status(TableStatus.EMPTY)
                .build();

        tableRepository.save(table);
    }
}