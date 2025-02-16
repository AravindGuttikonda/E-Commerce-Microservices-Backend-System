package com.productservice.product.services;

import com.productservice.product.dtos.CategoryRequestDTO;
import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.exceptions.CategoryNotAddedException;
import com.productservice.product.models.Category;
import com.productservice.product.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    @MockBean
    CategoryRepository categoryRepository;
    @Test
    public void getAllCategoriesTest() throws CategoriesNotFoundException {
        //Arrange
        List<Category> allCategories = new ArrayList<>();
        Mockito.when(categoryRepository.findAll()).thenReturn(allCategories);
        //Act
        List<Category> actualResult = categoryService.getAllCategories();
        //Assert
        Assertions.assertEquals(0, actualResult.size());
    }
    @Test
    public void addCategoryTest() throws CategoryNotAddedException {
        //Arrange
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        Category category = new Category();
        category.setName("One");
        Category savedCategory = new Category();
        savedCategory.setName("One");
        Mockito.when(categoryRepository.save(category)).thenReturn(savedCategory);
        //Act
        Category actualResult = categoryService.addCategory(categoryRequestDTO);
        //Assert
        Assertions.assertEquals("One", actualResult.getName());
    }
}