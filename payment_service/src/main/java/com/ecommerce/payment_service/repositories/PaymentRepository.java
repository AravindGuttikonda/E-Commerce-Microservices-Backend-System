package com.ecommerce.payment_service.repositories;

import com.ecommerce.payment_service.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentLinkId(String paymentLinkId);
}
