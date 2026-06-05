package com.qrorder.dto.food.request;

import com.qrorder.entity.enums.FoodType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data
public class CreateFoodRequest {

    @NotBlank(message = "Food name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    private String description;

    private String image;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Food type is required")
    private FoodType type;
}