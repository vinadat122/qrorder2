package com.qrorder.controller;

import com.qrorder.dto.table.CreateTableRequest;
import com.qrorder.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}