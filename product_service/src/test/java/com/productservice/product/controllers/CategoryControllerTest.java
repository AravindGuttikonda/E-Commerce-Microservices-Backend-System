package com.productservice.product.controllers;

import com.productservice.product.dtos.CategoriesResponseDTO;
import com.productservice.product.dtos.CategoryRequestDTO;
import com.productservice.product.dtos.CategoryResponseDTO;
import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.exceptions.CategoryNotAddedException;
import com.productservice.product.models.Category;
import com.productservice.product.services.CategoryService;
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
class CategoryControllerTest {
    @Autowired
    CategoryController categoryController;
    @MockBean
    CategoryService categoryService;
    @Test
    public void getAllCategoriesTest() throws CategoriesNotFoundException {
        //Arrange
        Mockito.when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());
        //Act
        ResponseEntity<CategoriesResponseDTO> actualResult = categoryController.getAllCategories();
        //Assert
        Assertions.assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }
    @Test
    public void addCategoryTest() throws CategoryNotAddedException {
        //Arrange
        Category category = new Category();
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        Mockito.when(categoryService.addCategory(categoryRequestDTO)).thenReturn(category);
        //Act
        ResponseEntity<CategoryResponseDTO> actualResult = categoryController.addCategory(categoryRequestDTO);
        //Assert
        Assertions.assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }
    @Test
    public void categoriesNotFoundExceptionTest() throws CategoriesNotFoundException {
        Mockito.when(categoryService.getAllCategories()).thenThrow(CategoriesNotFoundException.class);
        Assertions.assertThrows(CategoriesNotFoundException.class, () -> categoryController.getAllCategories());
    }
    @Test
    public void categoryNotAddedExceptionTest() throws CategoryNotAddedException {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        Mockito.when(categoryService.addCategory(categoryRequestDTO)).thenThrow(CategoryNotAddedException.class);
        Assertions.assertThrows(CategoryNotAddedException.class, () -> categoryController.addCategory(categoryRequestDTO));
    }
}
