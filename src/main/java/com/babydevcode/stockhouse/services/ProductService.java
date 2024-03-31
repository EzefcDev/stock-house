package com.babydevcode.stockhouse.services;

import java.util.List;

import com.babydevcode.stockhouse.dto.ProductDTO;

public interface ProductService {
    
    public List<ProductDTO> getProductsByCategory(String category);

    public List<ProductDTO> getProductsByName(String productName);

    public List<ProductDTO> getProducts();

    public void addProduct(ProductDTO productDto);

    public void updateProductAmount(String productName,Integer amount);

    public void deleteProduct(String productName);

}
