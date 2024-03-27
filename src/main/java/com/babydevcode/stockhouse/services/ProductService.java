package com.babydevcode.stockhouse.services;

import java.util.List;

import com.babydevcode.stockhouse.dto.ProductDTO;
import com.babydevcode.stockhouse.entities.Product;

public interface ProductService {
    
    public List<ProductDTO> getProductsByCategory(String category);

    public List<ProductDTO> getProducts();

    public void addProduct(Product product);

    public void updateProductAmount(Product product,Integer amount);

    public void deleteProduct(Product product);

}
