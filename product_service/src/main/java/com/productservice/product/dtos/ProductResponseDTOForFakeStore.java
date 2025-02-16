package com.productservice.product.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonSerialize
public class ProductResponseDTOForFakeStore implements Serializable {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}
