package com.ecommerce.order_service.dtos;

import com.ecommerce.order_service.models.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDto {
    private PaymentMethod paymentMethod;
    private Long customerId;
    private List<ProductRequestDto> productRequestDtoList;
}
