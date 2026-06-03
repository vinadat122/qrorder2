package com.qrorder.dto.food;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class FoodResponse {

    private Long id;

    private String name;

    private String type;

    private Double price;

    private String description;

    private String image;

    private Boolean available;

    private String categoryName;
}
