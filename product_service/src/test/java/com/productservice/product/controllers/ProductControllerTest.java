package com.productservice.product.controllers;

import com.productservice.product.dtos.ProductRequestDTOForFakeStore;
import com.productservice.product.dtos.ProductResponseDTO;
import com.productservice.product.dtos.ProductsResponseDTO;
import com.productservice.product.exceptions.*;
import com.productservice.product.models.Product;
import com.productservice.product.services.IProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    ProductController productController;
    @MockBean
    IProductService productService;
    @Test
    public void getSingleProductTest() throws ProductNotFoundException {
        //Arrange
        Product product = new Product();
        product.setId(1L);
        Mockito.when(productService.getSingleProduct(1L)).thenReturn(product);
        //Act
        ResponseEntity<ProductResponseDTO> actualResult = productController.getSingleProduct(1L);
        //Assert
        Assertions.assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }
    @Test
    public void getAllProductsTest() throws ProductsNotFoundException {
        //Arrange
        Mockito.when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        //Act
        ResponseEntity<ProductsResponseDTO> actualResult = productController.getAllProducts();
        //Assert
        Assertions.assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }
    @Test
    public void getAllProductsInCategoryTest() throws ProductsNotFoundInCategoryException {
        //Arrange
        Mockito.when(productService.getAllProductsInCategory("Jewelery")).thenReturn(new ArrayList<>());
        //Act
        ResponseEntity<ProductsResponseDTO> actualResult = productController.getAllProductsInCategory("Jewelery");
        //Assert
        Assertions.assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }
    @Test
    public void addProductTest() throws ProductNotAddedException, CategoryNotAddedException {
        //Arrange
        Product product = new Product();
        ProductRequestDTOForFakeStore productRequestDTOForFakeStore = new ProductRequestDTOForFakeStore();
        Mockito.when(productService.addProduct(productRequestDTOForFakeStore)).thenReturn(product);
        //Act
        ResponseEntity<ProductResponseDTO> actualResult = productController.addProduct(productRequestDTOForFakeStore);
        //Assert
        Assertions.assertEquals(HttpStatus.OK , actualResult.getStatusCode());
    }
    @Test
    public void updateProductTest() throws ProductNotAddedException, CategoryNotAddedException, ProductNotFoundException {
        //Arrange
        Product product = new Product();
        ProductRequestDTOForFakeStore productRequestDTOForFakeStore = new ProductRequestDTOForFakeStore();
        Mockito.when(productService.updateProduct(1L, productRequestDTOForFakeStore)).thenReturn(product);
        //Act
        ResponseEntity<ProductResponseDTO> actualResult = productController.updateProduct(1L, productRequestDTOForFakeStore);
        //Assert
        Assertions.assertEquals(HttpStatus.OK , actualResult.getStatusCode());
    }
    @Test
    public void productNotFoundExceptionTest() throws ProductNotFoundException {
        Mockito.when(productService.getSingleProduct(25L)).thenThrow(ProductNotFoundException.class);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productController.getSingleProduct(25L));
    }
    @Test
    public void productsNotFoundExceptionTest() throws ProductsNotFoundException {
        Mockito.when(productService.getAllProducts()).thenThrow(ProductsNotFoundException.class);
        Assertions.assertThrows(ProductsNotFoundException.class, () -> productController.getAllProducts());
    }
    @Test
    public void productsNotFoundInCategoryExceptionTest() throws ProductsNotFoundInCategoryException {
        Mockito.when(productService.getAllProductsInCategory(" ")).thenThrow(ProductsNotFoundInCategoryException.class);
        Assertions.assertThrows(ProductsNotFoundInCategoryException.class, () -> productController.getAllProductsInCategory(" "));
    }
    @Test
    public void productNotAddedExceptionTest() throws ProductNotAddedException, CategoryNotAddedException {
        ProductRequestDTOForFakeStore productRequestDTOForFakeStore = new ProductRequestDTOForFakeStore();
        Mockito.when(productService.addProduct(productRequestDTOForFakeStore)).thenThrow(ProductNotAddedException.class);
        Assertions.assertThrows(ProductNotAddedException.class, () -> productController.addProduct(productRequestDTOForFakeStore));
    }
}