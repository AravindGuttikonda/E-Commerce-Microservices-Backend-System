package com.ecommerce.payment_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentRequestDto {
    private Long orderId;
    private double amount;
    private String customerName;
    private String customerMail;
    private String customerPhone;
}
