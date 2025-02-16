package com.ecommerce.payment_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private double amount;
    private String customerName;
    private String customerMail;
    private String customerPhone;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String paymentLinkId;
}
