package com.qrorder.controller;

import com.qrorder.entity.TableSession;
import com.qrorder.service.TableSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class TableSessionController {

    private final TableSessionService
            tableSessionService;

    @PostMapping("/open/{tableId}")
    public Map<String, Long> openSession(
            @PathVariable Long tableId
    ) {

        TableSession session =
                tableSessionService
                        .openSession(tableId);

        return Map.of(
                "sessionId",
                session.getId()
        );
    }

    @GetMapping("/table/{tableId}/active")
    public Map<String, Long>
    getActiveSession(

            @PathVariable Long tableId
    ) {

        Long sessionId =
                tableSessionService
                        .getActiveSessionId(
                                tableId
                        );

        return Map.of(
                "sessionId",
                sessionId
        );
    }
}