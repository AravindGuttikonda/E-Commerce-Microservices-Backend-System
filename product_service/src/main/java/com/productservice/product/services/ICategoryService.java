package com.productservice.product.services;

import com.productservice.product.dtos.CategoryRequestDTO;
import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.exceptions.CategoryNotAddedException;
import com.productservice.product.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService {
    List<Category> getAllCategories() throws CategoriesNotFoundException;

    Category addCategory(CategoryRequestDTO categoryRequestDTO) throws CategoryNotAddedException;

    void deleteCategory(Long id);
}
