package com.ecommerce.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequestDto {
    private Long productId;
    private int quantity;
}
