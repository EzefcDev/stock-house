package com.babydevcode.stockhouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babydevcode.stockhouse.entities.Category;
import com.babydevcode.stockhouse.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findAllByCategory(Category category);

    Product findByNameProduct(String nameProduct);
    
}
