package com.qrorder.controller;

import com.qrorder.dto.category.CreateCategoryRequest;
import com.qrorder.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public String createCategory(
            @RequestBody CreateCategoryRequest request
    ) {

        categoryService.createCategory(request);

        return "Create category success";
    }
}