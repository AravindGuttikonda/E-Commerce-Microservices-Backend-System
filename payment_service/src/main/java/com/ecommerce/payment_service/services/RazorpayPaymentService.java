package com.ecommerce.payment_service.services;

import com.ecommerce.payment_service.dtos.EmailContent;
import com.ecommerce.payment_service.dtos.PaymentRequestDto;
import com.ecommerce.payment_service.models.Payment;
import com.ecommerce.payment_service.models.PaymentStatus;
import com.ecommerce.payment_service.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class RazorpayPaymentService implements PaymentService{
    @Autowired
    private RazorpayClient razorpayClient;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public String getPaymentLink(PaymentRequestDto paymentRequestDto) throws RazorpayException {

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",paymentRequestDto.getAmount()*100);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
        paymentLinkRequest.put("reference_id","" + paymentRequestDto.getOrderId());
        paymentLinkRequest.put("description","Payment for policy no " + paymentRequestDto.getOrderId());

        JSONObject customer = new JSONObject();
        customer.put("name",paymentRequestDto.getCustomerName());
        customer.put("contact","+91" + paymentRequestDto.getCustomerPhone());
        customer.put("email",paymentRequestDto.getCustomerMail());
        paymentLinkRequest.put("customer",customer);

        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

        Payment payment = new Payment();
        payment.setOrderId(paymentRequestDto.getOrderId());
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setPaymentStatus(PaymentStatus.INITIATED);
        payment.setCustomerMail(paymentRequestDto.getCustomerMail());
        payment.setCustomerName(paymentRequestDto.getCustomerName());
        payment.setCustomerPhone(paymentRequestDto.getCustomerPhone());
        payment.setPaymentLinkId(paymentLink.get("id"));
        paymentRepository.save(payment);

        return paymentLink.get("short_url");
    }

    @Override
    public PaymentStatus getPaymentStatus(String paymentLinkId) throws RazorpayException, JsonProcessingException {
        PaymentStatus paymentStatus;
        PaymentLink paymentLink = razorpayClient.paymentLink.fetch(paymentLinkId);
        if(paymentLink.get("status").equals("paid")){
            paymentStatus = PaymentStatus.SUCCESS;
        } else if (paymentLink.get("status").equals("created")) {
            paymentStatus = PaymentStatus.INITIATED;
        }else{
            paymentStatus = PaymentStatus.FAILURE;
        }
        Optional<Payment> paymentOptional = paymentRepository.findByPaymentLinkId(paymentLinkId);
        Payment savedPayment = paymentOptional.get();
        savedPayment.setPaymentStatus(paymentStatus);
        paymentRepository.save(savedPayment);
        String subject = "Payment Status";
        String body = "Payment is " + paymentStatus + " for order-id " + savedPayment.getOrderId();
        EmailContent emailContent = new EmailContent("guttikondaaravind39@gmail.com", savedPayment.getCustomerMail(), subject, body);
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(emailContent);
        kafkaTemplate.send("email-topic", valueAsString);
        return paymentStatus;
    }
}
