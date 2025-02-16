package com.productservice.product.services;

import com.productservice.product.dtos.ProductIdAndPrice;
import com.productservice.product.dtos.ProductRequestDTOForFakeStore;
import com.productservice.product.dtos.ProductResponseDTOForFakeStore;
import com.productservice.product.exceptions.ProductNotAddedException;
import com.productservice.product.exceptions.ProductNotFoundException;
import com.productservice.product.exceptions.ProductsNotFoundException;
import com.productservice.product.exceptions.ProductsNotFoundInCategoryException;
import com.productservice.product.models.Category;
import com.productservice.product.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FakeProductService implements IProductService{
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        ProductResponseDTOForFakeStore productResponse = null;
        if(!redisTemplate.opsForHash().hasKey(id, "hashKey")){
            productResponse = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, ProductResponseDTOForFakeStore.class);
            if(productResponse == null){
                throw new ProductNotFoundException();
            }
            redisTemplate.opsForHash().put(id, "hashKey", productResponse);
            redisTemplate.expire(id, 60, TimeUnit.SECONDS);
        }
        productResponse = (ProductResponseDTOForFakeStore)(redisTemplate.opsForHash().get(id, "hashKey"));

        Product product = new Product();
        product.setId(productResponse.getId());
        product.setName(productResponse.getTitle());
        product.setDescription(productResponse.getDescription());
        product.setPrice(productResponse.getPrice());
        product.setImageurl(productResponse.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productResponse.getCategory());
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws ProductsNotFoundException {
        ProductResponseDTOForFakeStore[] productsResponse = restTemplate.getForObject("https://fakestoreapi.com/products", ProductResponseDTOForFakeStore[
                ].class);
        if(productsResponse == null){
            throw new ProductsNotFoundException();
        }
        List<Product> products = new ArrayList<>();
        for(ProductResponseDTOForFakeStore productResponseDTO : productsResponse){
            Product product = new Product();
            product.setId(productResponseDTO.getId());
            product.setName(productResponseDTO.getTitle());
            product.setDescription(productResponseDTO.getDescription());
            product.setPrice(productResponseDTO.getPrice());
            product.setImageurl(productResponseDTO.getImage());
            product.setCategory(new Category());
            product.getCategory().setName(productResponseDTO.getCategory());
            products.add(product);
        }
        return products;
    }

    @Override
    public List<Product> getAllProductsInCategory(String categoryName) throws ProductsNotFoundInCategoryException {
        ProductResponseDTOForFakeStore[] productsResponse = restTemplate.getForObject("https://fakestoreapi.com/products/category/"+categoryName, ProductResponseDTOForFakeStore[
                ].class);
        if(productsResponse == null){
            throw new ProductsNotFoundInCategoryException();
        }
        List<Product> products = new ArrayList<>();
        for(ProductResponseDTOForFakeStore productResponseDTO : productsResponse){
            Product product = new Product();
            product.setId(productResponseDTO.getId());
            product.setName(productResponseDTO.getTitle());
            product.setDescription(productResponseDTO.getDescription());
            product.setPrice(productResponseDTO.getPrice());
            product.setImageurl(productResponseDTO.getImage());
            product.setCategory(new Category());
            product.getCategory().setName(productResponseDTO.getCategory());
            products.add(product);
        }
        return products;
    }

    @Override
    public Product addProduct(ProductRequestDTOForFakeStore productRequestDTO) throws ProductNotAddedException {
        ProductResponseDTOForFakeStore productResponseDTO = restTemplate.postForObject("https://fakestoreapi.com/products", productRequestDTO, ProductResponseDTOForFakeStore.class);
        if(productResponseDTO == null){
            throw new ProductNotAddedException();
        }
        Product product = new Product();
        product.setId(productResponseDTO.getId());
        product.setName(productResponseDTO.getTitle());
        product.setDescription(productResponseDTO.getDescription());
        product.setPrice(productResponseDTO.getPrice());
        product.setImageurl(productResponseDTO.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productResponseDTO.getCategory());
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDTOForFakeStore productRequestDTO) {
        ProductResponseDTOForFakeStore productResponseDTO = restTemplate.patchForObject("https://fakestoreapi.com/products/"+id, productRequestDTO, ProductResponseDTOForFakeStore.class);
        Product product = new Product();
        product.setId(productResponseDTO.getId());
        product.setName(productResponseDTO.getTitle());
        product.setDescription(productResponseDTO.getDescription());
        product.setPrice(productResponseDTO.getPrice());
        product.setImageurl(productResponseDTO.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productResponseDTO.getCategory());
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }

    @Override
    public Page<Product> getAllProductsByPagingAndSorting(int pageNumber, int pageSize, String sortBy) {
        return null;
    }

    @Override
    public List<ProductIdAndPrice> getAllProductsByIds(List<Long> productids) {
        return null;
    }
}
