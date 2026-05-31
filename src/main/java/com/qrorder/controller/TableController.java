package com.qrorder.controller;

import com.qrorder.dto.table.request.CreateTableRequest;
import com.qrorder.dto.table.request.ReserveTableRequest;
import com.qrorder.entity.RestaurantTable;
import com.qrorder.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping
    public String createTable(
            @RequestBody CreateTableRequest request
    ) {

        tableService.createTable(request);

        return "Create table success";
    }

    @PostMapping("/{tableId}/reserve")
    public String reserveTable(

            @PathVariable Long tableId,

            @RequestBody
            ReserveTableRequest request
    ) {

        tableService.reserveTable(
                tableId,
                request
        );

        return "Reserve table success";
    }

    @GetMapping
    public List<RestaurantTable> getTables() {

        return tableService.getTables();
    }

    @PostMapping("/{tableId}/checkin")
    public Map<String, Long> checkIn(

                                     @PathVariable Long tableId
    ) {

        Long sessionId =
                tableService.checkIn(tableId);

        return Map.of(
                "sessionId",
                sessionId
        );
    }

    @PostMapping("/{tableId}/reset")
    public String resetTable(

            @PathVariable Long tableId
    ) {

        tableService.resetTable(
                tableId
        );

        return "Table reset success";
    }
}