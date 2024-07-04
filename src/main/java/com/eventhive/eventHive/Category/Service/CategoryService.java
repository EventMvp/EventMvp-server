package com.eventhive.eventHive.Category.Service;

import com.eventhive.eventHive.Category.Entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById (Long categoryId);
    List<Category> getAllCategory();
}
