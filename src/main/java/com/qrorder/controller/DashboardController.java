package com.qrorder.controller;

import com.qrorder.dto.dashboard.DashboardResponse;
import com.qrorder.service.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor

public class DashboardController {

    private final DashboardService
            dashboardService;

    @GetMapping
    public DashboardResponse getDashboard() {

        return dashboardService
                .getDashboard();
    }
}