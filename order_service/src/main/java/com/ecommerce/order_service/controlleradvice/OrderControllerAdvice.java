package com.ecommerce.order_service.controlleradvice;

import com.ecommerce.order_service.dtos.OrderResponseDto;
import com.ecommerce.order_service.exceptions.CustomerNotPresentException;
import com.ecommerce.order_service.exceptions.ProductsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderControllerAdvice {
    @ExceptionHandler(CustomerNotPresentException.class)
    public ResponseEntity<OrderResponseDto> handleCustomerNotPresentException(){
        return new ResponseEntity<>(new OrderResponseDto(null, "Customer is not present"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity<OrderResponseDto> handleProductsNotFoundException(){
        return new ResponseEntity<>(new OrderResponseDto(null, "Products are not present"), HttpStatus.NOT_FOUND);
    }
}
