package com.productservice.product.dtos;

import com.productservice.product.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponseDTO {
    private Product product;
    private String message;
}
