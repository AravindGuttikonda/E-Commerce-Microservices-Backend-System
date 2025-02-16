package com.ecommerce.payment_service.services;

import com.ecommerce.payment_service.dtos.PaymentRequestDto;
import com.ecommerce.payment_service.models.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    String getPaymentLink(PaymentRequestDto paymentRequestDto) throws RazorpayException;

    PaymentStatus getPaymentStatus(String paymentId) throws RazorpayException, JsonProcessingException;
}
