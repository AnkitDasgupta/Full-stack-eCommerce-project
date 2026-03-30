package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.ProductException;
import com.EcommerceSite.Ecommerce.website.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public ProductEntity createProduct(CreateProductRequest req, SellerEntity seller);
    void deleteProduct(Long productId) throws ProductException;

    ProductEntity updateProduct(Long productId, ProductEntity product) throws ProductException;

    ProductEntity findProductById(Long productId) throws ProductException;

    List<ProductEntity> searchProducts(String query);

    Page<ProductEntity> getAllProducts(
            String category,
            String brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );

    List<ProductEntity> getProductBySellerId(Long sellerId);

}
