package com.productservice.product.services;

import com.productservice.product.dtos.ProductIdAndPrice;
import com.productservice.product.dtos.ProductRequestDTOForFakeStore;
import com.productservice.product.exceptions.*;
import com.productservice.product.models.Category;
import com.productservice.product.models.Product;
import com.productservice.product.repositories.CategoryRepository;
import com.productservice.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Service
@Primary
public class ProductService implements IProductService{
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> product = null;
        if(!redisTemplate.opsForHash().hasKey(id, "hashKey")){
            product = productRepository.findById(id);
            if(product.isEmpty()){
                throw new ProductNotFoundException();
            }
            redisTemplate.opsForHash().put(id, "hashKey", product);
            redisTemplate.expire(id, 60, TimeUnit.SECONDS);
        }
        product = (Optional<Product>)redisTemplate.opsForHash().get(id, "hashKey");
        return product.get();
    }

    @Override
    public List<Product> getAllProducts() throws ProductsNotFoundException {
        List<Product> allProducts = productRepository.findAll();
        if(allProducts == null){
            throw new ProductsNotFoundException();
        }
        return allProducts;
    }

    @Override
    public List<Product> getAllProductsInCategory(String categoryName) throws ProductsNotFoundInCategoryException {
        List<Product> allProducts = productRepository.findAll();
        if(allProducts == null){
            throw new ProductsNotFoundInCategoryException();
        }
        List<Product> productsInCategory = allProducts.stream().filter((product -> {
            return product.getCategory().getName().equalsIgnoreCase(categoryName);
        })).collect(Collectors.toList());
        return productsInCategory;
    }

    @Override
    public Product addProduct(ProductRequestDTOForFakeStore productRequestDTOForFakeStore) throws ProductNotAddedException, CategoryNotAddedException {
        Product product = new Product();
        Optional<Category> category = categoryRepository.findByName(productRequestDTOForFakeStore.getCategory());
        if(category.isEmpty()){
            Category newCategory = new Category();
            newCategory.setName(productRequestDTOForFakeStore.getCategory());
            Category savedCategory = categoryRepository.save(newCategory);
            if(savedCategory == null){
                throw new CategoryNotAddedException();
            }
            product.setCategory(savedCategory);
        }
        else{
            product.setCategory(category.get());
        }
        product.setImageurl(productRequestDTOForFakeStore.getImage());
        product.setName(productRequestDTOForFakeStore.getTitle());
        product.setPrice(productRequestDTOForFakeStore.getPrice());
        product.setDescription(productRequestDTOForFakeStore.getDescription());
        Product savedProduct = productRepository.save(product);
        if(savedProduct == null){
            throw new ProductNotAddedException();
        }
        return savedProduct;
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDTOForFakeStore productRequestDTOForFakeStore) throws ProductNotFoundException, ProductNotAddedException, CategoryNotAddedException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException();
        }
        Product actualProduct = product.get();
        Optional<Category> category = categoryRepository.findByName(productRequestDTOForFakeStore.getCategory());
        if(category.isEmpty()){
            Category newCategory = new Category();
            newCategory.setName(productRequestDTOForFakeStore.getCategory());
            Category savedCategory = categoryRepository.save(newCategory);
            if(savedCategory == null){
                throw new CategoryNotAddedException();
            }
            actualProduct.setCategory(savedCategory);
        }
        else{
            actualProduct.setCategory(category.get());
        }
        actualProduct.setImageurl(productRequestDTOForFakeStore.getImage());
        actualProduct.setName(productRequestDTOForFakeStore.getTitle());
        actualProduct.setPrice(productRequestDTOForFakeStore.getPrice());
        actualProduct.setDescription(productRequestDTOForFakeStore.getDescription());
        Product updatedProduct = productRepository.save(actualProduct);
        if(updatedProduct == null){
            throw new ProductNotAddedException();
        }
        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getAllProductsByPagingAndSorting(int pageNumber, int pageSize, String sortBy) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
    }

    @Override
    public List<ProductIdAndPrice> getAllProductsByIds(List<Long> productIds) {
        List<Product> allById = productRepository.findAllById(productIds);
        List<ProductIdAndPrice> productIdAndPrices = new ArrayList<>();
        for(Product product : allById){
            ProductIdAndPrice productIdAndPrice = new ProductIdAndPrice();
            productIdAndPrice.setProductId(product.getId());
            productIdAndPrice.setPrice(product.getPrice());
            productIdAndPrices.add(productIdAndPrice);
        }
        return productIdAndPrices;
    }
}
