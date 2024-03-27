package com.babydevcode.stockhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babydevcode.stockhouse.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

    Category findByNameCategory(String category);
    
}
