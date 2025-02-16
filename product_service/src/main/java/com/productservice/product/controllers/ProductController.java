package com.productservice.product.controllers;

import com.productservice.product.dtos.*;
import com.productservice.product.exceptions.*;
import com.productservice.product.models.Product;
import com.productservice.product.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @GetMapping("/getSingleProduct/{id}")
    public ResponseEntity<ProductResponseDTO> getSingleProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(id);
        return new ResponseEntity<>(new ProductResponseDTO(product, "Success") , HttpStatus.OK);
    }
    @GetMapping("/getAllProducts")
    public ResponseEntity<ProductsResponseDTO> getAllProducts() throws ProductsNotFoundException {
        List<Product> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(new ProductsResponseDTO(allProducts, "Success"), HttpStatus.OK);
    }
    @PostMapping("/getAllProductsByIds")
    public ResponseEntity<List<ProductIdAndPrice>> getProductsByIds(@RequestBody List<Long> productIds){
        List<ProductIdAndPrice> allProductsByIds = productService.getAllProductsByIds(productIds);
        return new ResponseEntity<>(allProductsByIds, HttpStatus.OK);
    }
    @GetMapping("/getAllProductsByPagingAndSorting")
    public ResponseEntity<ProductsResponseByPageDTO> getAllProductsByPagingAndSorting(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortBy){
        Page<Product> allProducts = productService.getAllProductsByPagingAndSorting(pageNumber, pageSize, sortBy);
        return new ResponseEntity<>(new ProductsResponseByPageDTO(allProducts, "Success"), HttpStatus.OK);
    }
    @GetMapping("/getAllProductsInCategory/{categoryName}")
    public ResponseEntity<ProductsResponseDTO> getAllProductsInCategory(@PathVariable("categoryName") String categoryName) throws ProductsNotFoundInCategoryException {
        List<Product> allProductsInCategory = productService.getAllProductsInCategory(categoryName);
        return new ResponseEntity<>(new ProductsResponseDTO(allProductsInCategory, "Success"), HttpStatus.OK);
    }
    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTOForFakeStore productRequestDTOForFakeStore) throws ProductNotAddedException, CategoryNotAddedException {
        Product product = productService.addProduct(productRequestDTOForFakeStore);
        return new ResponseEntity<>(new ProductResponseDTO(product, "Success"), HttpStatus.OK);
    }
    @PatchMapping("/updateProduct/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id , @RequestBody ProductRequestDTOForFakeStore productRequestDTOForFakeStore) throws ProductNotAddedException, ProductNotFoundException, CategoryNotAddedException {
        Product product = productService.updateProduct(id, productRequestDTOForFakeStore);
        return new ResponseEntity<>(new ProductResponseDTO(product, "Success"), HttpStatus.OK);
    }
    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }
}
