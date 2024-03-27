package com.babydevcode.stockhouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.babydevcode.stockhouse.dto.ProductDTO;
import com.babydevcode.stockhouse.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "category.nameCategory")
    ProductDTO productToDto(Product product);

    @Mapping(target = "category", ignore = true)
    Product dtoToProduct(ProductDTO productDTO);
}
