package com.babydevcode.stockhouse.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.babydevcode.stockhouse.dto.ProductDTO;

public interface ProductService {

    public Page<ProductDTO> getProductsByCategoryOrSearch(String category,String productName, Integer page);

    public List<ProductDTO> getProducts();
    
    public void addProduct(ProductDTO productDto);

    public void updateProductAmount(String productName,Integer amount);

    public void deleteProduct(String productName);

    public boolean getProduct(String productName);

    public List<ProductDTO> createFile();

}
