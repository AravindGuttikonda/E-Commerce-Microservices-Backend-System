package com.productservice.product.controlleradvice;

import com.productservice.product.dtos.CategoriesResponseDTO;
import com.productservice.product.dtos.CategoryResponseDTO;
import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.exceptions.CategoryNotAddedException;
import com.productservice.product.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public class CategoryControllerAdvice {
    @ExceptionHandler(CategoriesNotFoundException.class)
    public ResponseEntity<CategoriesResponseDTO>  handleCategoriesNotFoundException(){
        return new ResponseEntity<>(new CategoriesResponseDTO(null, "Categories not found from controller advice"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryNotAddedException.class)
    public ResponseEntity<CategoryResponseDTO> handleCategoryNotAddedException(){
        return new ResponseEntity<>(new CategoryResponseDTO(null, "Category not added from controller advice"), HttpStatus.NOT_FOUND);
    }
}
