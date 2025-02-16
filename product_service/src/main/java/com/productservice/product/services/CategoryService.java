package com.productservice.product.services;

import com.productservice.product.dtos.CategoryRequestDTO;
import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.exceptions.CategoryNotAddedException;
import com.productservice.product.models.Category;
import com.productservice.product.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService implements ICategoryService{
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() throws CategoriesNotFoundException {
        List<Category> allCategories = categoryRepository.findAll();
        if(allCategories == null){
            throw new CategoriesNotFoundException();
        }
        return allCategories;
    }

    @Override
    public Category addCategory(CategoryRequestDTO categoryRequestDTO) throws CategoryNotAddedException {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        if(savedCategory == null){
            throw new CategoryNotAddedException();
        }
        return category;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
