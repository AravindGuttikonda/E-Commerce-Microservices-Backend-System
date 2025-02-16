package com.productservice.product.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTOForFakeStore {
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}
