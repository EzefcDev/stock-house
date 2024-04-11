package com.babydevcode.stockhouse.repositories;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.babydevcode.stockhouse.entities.Category;
import com.babydevcode.stockhouse.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Page<Product> findAllByCategory(Category category, Pageable pageable);
    
    Page<Product> findByNameProductContaining(String nameProduct, Pageable pageable);

    Optional<Product> findByNameProduct(String nameProduct);

}
