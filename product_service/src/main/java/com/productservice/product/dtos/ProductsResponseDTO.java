package com.productservice.product.dtos;

import com.productservice.product.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductsResponseDTO {
    private List<Product> products;
    private String message;
}
