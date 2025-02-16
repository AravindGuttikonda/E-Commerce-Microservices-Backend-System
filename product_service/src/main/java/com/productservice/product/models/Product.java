package com.productservice.product.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends BaseModel{
    private String description;
    private double price;
    private String imageurl;
    @ManyToOne
    private Category category;
}
