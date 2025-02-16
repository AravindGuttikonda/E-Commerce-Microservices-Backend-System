package com.ecommerce.payment_service.controllers;

import com.ecommerce.payment_service.services.PaymentService;
import com.ecommerce.payment_service.dtos.PaymentRequestDto;
import com.ecommerce.payment_service.models.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/getPaymentLink")
    public ResponseEntity<String> getPaymentLink(@RequestBody PaymentRequestDto paymentRequestDto) throws RazorpayException {
        String paymentLink = paymentService.getPaymentLink(paymentRequestDto);
        return new ResponseEntity<>(paymentLink, HttpStatus.CREATED);
    }
    @GetMapping("/getPaymentStatus/{paymentLinkId}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable("paymentLinkId") String paymentLinkId) throws RazorpayException, JsonProcessingException {
        PaymentStatus paymentStatus = paymentService.getPaymentStatus(paymentLinkId);
        return new ResponseEntity<>(paymentStatus, HttpStatus.OK);
    }
}
