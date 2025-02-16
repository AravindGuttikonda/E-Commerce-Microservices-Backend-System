package com.ecommerce.order_service.controllers;

import com.ecommerce.order_service.dtos.OrderRequestDto;
import com.ecommerce.order_service.dtos.OrderResponseDto;
import com.ecommerce.order_service.exceptions.CustomerNotPresentException;
import com.ecommerce.order_service.exceptions.ProductsNotFoundException;
import com.ecommerce.order_service.models.Order;
import com.ecommerce.order_service.services.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) throws CustomerNotPresentException, ProductsNotFoundException, JsonProcessingException {
        Order order = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(new OrderResponseDto(order, "Order created successfully"), HttpStatus.CREATED);
    }
}
