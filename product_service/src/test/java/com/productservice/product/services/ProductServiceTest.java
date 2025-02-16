package com.productservice.product.services;

import com.productservice.product.dtos.ProductRequestDTOForFakeStore;
import com.productservice.product.dtos.ProductResponseDTO;
import com.productservice.product.exceptions.*;
import com.productservice.product.models.Category;
import com.productservice.product.models.Product;
import com.productservice.product.repositories.CategoryRepository;
import com.productservice.product.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductService productService;
    @MockBean
    ProductRepository productRespository;
    @MockBean
    CategoryRepository categoryRepository;
    @Test
    public void getSingleProductTest() throws ProductNotFoundException {
        //Arrange
        Product product = new Product();
        Optional<Product> optionalProduct = Optional.of(product);
        Mockito.when(productRespository.findById(1L)).thenReturn(optionalProduct);
        //Act
        Product singleProduct = productService.getSingleProduct(1L);
        //Assert
        Assertions.assertTrue(singleProduct != null);
    }
    @Test
    public void getAllProductsTest() throws ProductsNotFoundException {
        //Arrange
        Mockito.when(productRespository.findAll()).thenReturn(new ArrayList<>());
        //Act
        List<Product> allProducts = productService.getAllProducts();
        //Assert
        Assertions.assertEquals(0, allProducts.size());
    }
    @Test
    public void getAllProductsInCategoryTest() throws ProductsNotFoundInCategoryException {
        //Arrange
        Mockito.when(productRespository.findAll()).thenReturn(new ArrayList<>());
        //Act
        List<Product> actualResult = productService.getAllProductsInCategory("One");
        //Assert
        Assertions.assertEquals(0, actualResult.size());
    }
    @Test
    public void addProductTest() throws ProductNotAddedException, CategoryNotAddedException {
        //Arrange
        ProductRequestDTOForFakeStore productRequestDTOForFakeStore = new ProductRequestDTOForFakeStore();
        Product product = new Product();
        product.setName("One");
        Product savedProduct = new Product();
        savedProduct.setName("One");
        Mockito.when(categoryRepository.findByName("One")).thenReturn(Optional.of(new Category()));
        Mockito.when(productRespository.save(product)).thenReturn(savedProduct);
        //Act
        Product actualProduct = productService.addProduct(productRequestDTOForFakeStore);
        //Assert
        Assertions.assertEquals("One", actualProduct.getName());
    }
}