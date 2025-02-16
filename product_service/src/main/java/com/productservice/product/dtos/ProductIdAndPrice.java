package com.productservice.product.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductIdAndPrice {
    private Long productId;
    private double price;
}
