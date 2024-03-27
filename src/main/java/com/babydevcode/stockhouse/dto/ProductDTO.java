package com.babydevcode.stockhouse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Integer idProduct;
    private String nameProduct;
    private Integer amountProduct;
    private String category;
}
