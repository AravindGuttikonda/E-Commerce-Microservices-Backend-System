package com.ecommerce.order_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductIdAndPrice {
    private Long productId;
    private double price;
}
