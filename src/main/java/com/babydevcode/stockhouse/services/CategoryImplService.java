package com.babydevcode.stockhouse.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babydevcode.stockhouse.entities.Category;
import com.babydevcode.stockhouse.repositories.CategoryRepository;

@Service
public class CategoryImplService implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<String> allCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<String> filterCategories= new ArrayList<String>();
        for (Category category : categories) {
            filterCategories.add(category.getNameCategory());
        }
        return filterCategories;
    }

    @Override
    public Category getCategoryByName(String name) {
        Category categoryEntity = categoryRepository.findByNameCategory(name);
        return categoryEntity;
    }

}
