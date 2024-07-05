package com.eventhive.eventHive.Category.Service.Impl;

import com.eventhive.eventHive.Category.Dto.CategoryRespDto;
import com.eventhive.eventHive.Category.Entity.Category;
import com.eventhive.eventHive.Category.Repository.CategoryRepository;
import com.eventhive.eventHive.Category.Service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<CategoryRespDto> getAllCategory() {
        List<Category> categoryList = repository.findAll();

        return categoryList.stream()
                .map(CategoryRespDto::convertDto)
                .collect(Collectors.toList());
    }
}
