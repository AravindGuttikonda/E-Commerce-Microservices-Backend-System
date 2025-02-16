package com.productservice.product.services;

import com.productservice.product.dtos.CategoryRequestDTO;
import com.productservice.product.exceptions.CategoriesNotFoundException;
import com.productservice.product.exceptions.CategoryNotAddedException;
import com.productservice.product.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class FakeCategoryService implements ICategoryService{
    @Autowired
    RestTemplate restTemplate;
    @Override
    public List<Category> getAllCategories() throws CategoriesNotFoundException {
        String[] categoriesResponse = restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);
        if(categoriesResponse == null){
            throw new CategoriesNotFoundException();
        }
        List<Category> categories = new ArrayList<>();
        for (String s : categoriesResponse){
            Category category = new Category();
            category.setName(s);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category addCategory(CategoryRequestDTO categoryRequestDTO) throws CategoryNotAddedException {
        throw new CategoryNotAddedException();
    }

    @Override
    public void deleteCategory(Long id) {
        return;
    }
}
