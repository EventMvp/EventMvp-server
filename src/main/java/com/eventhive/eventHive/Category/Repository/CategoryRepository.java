package com.eventhive.eventHive.Category.Repository;

import com.eventhive.eventHive.Category.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
