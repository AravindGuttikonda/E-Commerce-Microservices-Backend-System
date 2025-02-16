package com.productservice.product.dtos;

import com.productservice.product.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class CategoriesResponseDTO {
    private List<Category> categories;
    private String Message;
}
