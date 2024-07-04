package com.eventhive.eventHive.Category.Controller;

import com.eventhive.eventHive.Category.Service.CategoryService;
import com.eventhive.eventHive.Response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        return Response.successResponse("Get All Categories", categoryService.getAllCategory());
    }
}