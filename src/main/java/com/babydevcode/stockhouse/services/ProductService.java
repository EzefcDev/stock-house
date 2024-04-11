package com.babydevcode.stockhouse.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.babydevcode.stockhouse.dto.ProductDTO;
import com.babydevcode.stockhouse.entities.Product;

public interface ProductService {
    
    public Page<ProductDTO> getProductsByCategory(String category, Integer page);

    public Page<ProductDTO> getProductsByName(String productName, Integer page);

    public List<ProductDTO> getProducts();
    
    public Page<ProductDTO> getProductsPage(Integer page);

    public void addProduct(ProductDTO productDto);

    public void updateProductAmount(String productName,Integer amount);

    public void deleteProduct(String productName);

    public Product getProduct(String productName);

}
