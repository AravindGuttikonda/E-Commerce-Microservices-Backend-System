package com.ecommerce.order_service.dtos;

import com.ecommerce.order_service.models.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDto {
    private Order order;
    private String message;
}
