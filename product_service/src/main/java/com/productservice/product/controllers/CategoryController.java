package com.productservice.product.controllers;

import com.productservice.product.dtos.CategoriesResponseDTO;
import com.productservice.product.dtos.CategoryRequestDTO;
import com.productservice.product.dtos.CategoryResponseDTO;
import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.exceptions.CategoryNotAddedException;
import com.productservice.product.models.Category;
import com.productservice.product.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<CategoriesResponseDTO> getAllCategories() throws CategoriesNotFoundException {
        List<Category> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(new CategoriesResponseDTO(allCategories, "Success"), HttpStatus.OK);
    }
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> addCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) throws CategoryNotAddedException {
        Category category = categoryService.addCategory(categoryRequestDTO);
        return new ResponseEntity<>(new CategoryResponseDTO(category, "Success"), HttpStatus.OK);
    }
    @DeleteMapping("/categories")
    public void deleteCategory(Long id){
        categoryService.deleteCategory(id);
    }
}
