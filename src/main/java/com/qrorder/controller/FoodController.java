package com.qrorder.controller;

import com.qrorder.dto.food.CreateFoodRequest;
import com.qrorder.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public String createFood(
            @RequestBody CreateFoodRequest request
    ) {

        foodService.createFood(request);

        return "Create food success";
    }
}