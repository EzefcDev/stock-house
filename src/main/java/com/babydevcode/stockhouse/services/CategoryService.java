package com.babydevcode.stockhouse.services;

import java.util.List;

import com.babydevcode.stockhouse.entities.Category;

public interface CategoryService {
    
    public List<String> allCategories();

    public Category getCategoryByName(String name);
}
