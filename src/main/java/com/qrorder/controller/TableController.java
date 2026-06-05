package com.qrorder.controller;

import com.qrorder.dto.table.request.CreateTableRequest;
import com.qrorder.dto.table.request.ReserveTableRequest;
import com.qrorder.dto.table.response.TableResponse;

import com.qrorder.entity.RestaurantTable;
import com.qrorder.service.TableService;
import jakarta.validation.Valid;
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

            @Valid
            @RequestBody CreateTableRequest request
    ) {

        tableService.createTable(request);

        return "Create table success";
    }

    @PostMapping("/{tableId}/reserve")
    public String reserveTable(

            @PathVariable Long tableId,

            @Valid
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
    public List<TableResponse> getTables() {

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

    @PutMapping ("/{tableId}/reset")
    public String resetTable(

            @PathVariable Long tableId
    ) {

        tableService.resetTable(
                tableId
        );

        return "Table reset success";
    }
}