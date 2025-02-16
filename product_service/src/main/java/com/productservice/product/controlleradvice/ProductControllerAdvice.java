package com.productservice.product.controlleradvice;

import com.productservice.product.dtos.ProductResponseDTO;
import com.productservice.product.dtos.ProductsResponseDTO;
import com.productservice.product.exceptions.ProductNotAddedException;
import com.productservice.product.exceptions.ProductNotFoundException;
import com.productservice.product.exceptions.ProductsNotFoundException;
import com.productservice.product.exceptions.ProductsNotFoundInCategoryException;
import com.productservice.product.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ProductControllerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductResponseDTO> handleProductNotFoundException(){
        return new ResponseEntity<>(new ProductResponseDTO(null, "Product not found from controller advice") , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity<ProductsResponseDTO> handleProductsNotFoundException(){
        return new ResponseEntity<>(new ProductsResponseDTO(null, "Products not found from controller advice"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductsNotFoundInCategoryException.class)
    public ResponseEntity<ProductsResponseDTO> handleProductsNotFoundInCategoryException(){
        return new ResponseEntity<>(new ProductsResponseDTO(null, "Products not found from controller advice"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotAddedException.class)
    public ResponseEntity<ProductResponseDTO> handleProductNotAddedException(){
        return new ResponseEntity<>(new ProductResponseDTO(null, "Product not added from controller advice"), HttpStatus.NOT_ACCEPTABLE);
    }
}
