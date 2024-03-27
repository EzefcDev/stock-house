package com.babydevcode.stockhouse.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babydevcode.stockhouse.dto.ProductDTO;
import com.babydevcode.stockhouse.entities.Category;
import com.babydevcode.stockhouse.entities.Product;
import com.babydevcode.stockhouse.mapper.ProductMapper;
import com.babydevcode.stockhouse.repositories.ProductRepository;

@Service
public class ProductImpService implements ProductService {
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        Category categoryEntity = categoryService.getCategoryByName(category);
        List<Product> products = productRepository.findAllByCategory(categoryEntity);
        List<ProductDTO> productDTOs = new ArrayList();
        for (Product product : products) {
            productDTOs.add(productMapper.productToDto(product));
        }
        return productDTOs;
    }

    @Override
    public List<ProductDTO> getProducts() {
       List<Product> products = productRepository.findAll();
       List<ProductDTO> productDTOs = new ArrayList();
        for (Product product : products) {
            productDTOs.add(productMapper.productToDto(product));
        }
       return productDTOs;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProductAmount(Product product, Integer amount) {
        Product productSearched = productRepository.findById(product.getIdProduct()).orElse(null);
        productSearched.setAmountProduct(amount);
        productRepository.save(productSearched);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

}
