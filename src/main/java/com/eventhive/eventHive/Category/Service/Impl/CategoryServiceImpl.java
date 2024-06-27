package com.eventhive.eventHive.Category.Service.Impl;

import com.eventhive.eventHive.Category.Entity.Category;
import com.eventhive.eventHive.Category.Repository.CategoryRepository;
import com.eventhive.eventHive.Category.Service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return repository.findById(categoryId).orElseThrow(() -> new RuntimeException("category not available"));
    }
}
