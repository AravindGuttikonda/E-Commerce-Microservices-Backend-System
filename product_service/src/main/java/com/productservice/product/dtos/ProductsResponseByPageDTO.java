package com.productservice.product.dtos;

import com.productservice.product.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
@Getter
@Setter
@AllArgsConstructor
public class ProductsResponseByPageDTO {
    private Page<Product> productPage;
    private String message;
}
