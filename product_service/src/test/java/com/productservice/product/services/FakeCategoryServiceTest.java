package com.productservice.product.services;

import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.models.Category;
import com.productservice.product.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FakeCategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    @MockBean
    RestTemplate restTemplate;
    @Test
    public void getAllCategoriesTest() throws CategoriesNotFoundException {
        //Arrange
        String[] allCategories = new String[0];
        Mockito.when(restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class)).thenReturn(allCategories);
        //Act
        List<Category> actualResult = categoryService.getAllCategories();
        //Assert
        Assertions.assertEquals(0, actualResult.size());
    }
}