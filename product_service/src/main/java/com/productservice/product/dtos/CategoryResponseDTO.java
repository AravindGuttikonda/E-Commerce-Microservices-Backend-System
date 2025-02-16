package com.productservice.product.dtos;

import com.productservice.product.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryResponseDTO {
    private Category category;
    private String message;
}
