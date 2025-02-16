package com.productservice.product.services;

import com.productservice.product.dtos.ProductIdAndPrice;
import com.productservice.product.dtos.ProductRequestDTOForFakeStore;
import com.productservice.product.exceptions.*;
import com.productservice.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    Product getSingleProduct(Long id) throws ProductNotFoundException;

    List<Product> getAllProducts() throws ProductsNotFoundException;

    List<Product> getAllProductsInCategory(String categoryName) throws ProductsNotFoundInCategoryException;

    Product addProduct(ProductRequestDTOForFakeStore productRequestDTOForFakeStore) throws ProductNotAddedException, CategoryNotAddedException;

    Product updateProduct(Long id, ProductRequestDTOForFakeStore productRequestDTOForFakeStore) throws ProductNotFoundException, ProductNotAddedException, CategoryNotAddedException;

    void deleteProduct(Long id);

    Page<Product> getAllProductsByPagingAndSorting(int pageNumber, int pageSize, String sortBy);

    List<ProductIdAndPrice> getAllProductsByIds(List<Long> productIds);
}
