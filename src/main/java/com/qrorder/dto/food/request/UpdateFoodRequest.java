package com.qrorder.dto.food;

import com.qrorder.entity.enums.FoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateFoodRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Double price;

    private String image;

    @NotNull
    private Long categoryId;

    @NotNull(message = "Food type is required")
    private FoodType type;
}