package com.babydevcode.stockhouse.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return transformProductToDTO(products);
    }

    @Override
    public List<ProductDTO> getProducts() {
       List<Product> products = productRepository.findAll();
       return transformProductToDTO(products);
    }

    @Override
    public void addProduct(ProductDTO productDto) {
        Category categoryEntity = categoryService.getCategoryByName(productDto.getCategory());
        Product product = productMapper.dtoToProduct(productDto);
        product.setCategory(categoryEntity);
        productRepository.save(product);
    }

    @Override
    public void updateProductAmount(String productName, Integer amount) {
        Product productSearched = productRepository.findByNameProduct(productName);
        productSearched.setAmountProduct(amount);
        productRepository.save(productSearched);
    }

    @Override
    public void deleteProduct(String productName) {
        Product product = productRepository.findByNameProduct(productName);
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getProductsByName(String productName) {
        List<Product> products =productRepository.findByNameProductContaining(productName);
        return transformProductToDTO(products);
    }

    private List<ProductDTO> transformProductToDTO(List<Product> products){
        List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
        for (Product product : products) {
            ProductDTO productDTO = productMapper.productToDto(product);
            productDTOs.add(productDTO);
        }
       return productDTOs;
    }

    @Override
    public Product getProduct(String productName) {
        Product product = productRepository.findByNameProduct(productName);
        return product;
    }

    @Override
    public Page<ProductDTO> getProductsPage(Integer page) {
        Integer size = 8 ;
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> listProducts = productRepository.findAll(pageable);
        return productMapper.productsToProductDTOs(listProducts);
    }

}
