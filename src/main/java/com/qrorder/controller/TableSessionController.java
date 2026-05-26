package com.qrorder.controller;

import com.qrorder.service.TableSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class TableSessionController {

    private final TableSessionService sessionService;

    @PostMapping("/open/{tableId}")
    public String openSession(
            @PathVariable Long tableId
    ) {

        sessionService.openSession(tableId);

        return "Open session success";
    }
}