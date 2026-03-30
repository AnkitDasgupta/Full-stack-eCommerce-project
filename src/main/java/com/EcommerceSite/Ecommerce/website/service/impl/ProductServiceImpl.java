package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.CategoryEntity;
import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.ProductException;
import com.EcommerceSite.Ecommerce.website.repository.CategoryRepository;
import com.EcommerceSite.Ecommerce.website.repository.ProductRepository;
import com.EcommerceSite.Ecommerce.website.request.CreateProductRequest;
import com.EcommerceSite.Ecommerce.website.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductEntity createProduct(CreateProductRequest req, SellerEntity seller) {

        CategoryEntity category1 = categoryRepository.findByCategoryId(req.getCategory());

        if (category1 == null) {
            CategoryEntity category = new CategoryEntity();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1 = categoryRepository.save(category);
        }
        CategoryEntity category2 = categoryRepository.findByCategoryId(req.getCategory2());

        if (category2 == null) {
            CategoryEntity category = new CategoryEntity();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepository.save(category);
        }
        CategoryEntity category3 = categoryRepository.findByCategoryId(req.getCategory3());

        if (category3 == null) {
            CategoryEntity category = new CategoryEntity();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepository.save(category);
        }

        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());

        ProductEntity product = new ProductEntity();

        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSizes());
        product.setDiscountPercent(discountPercentage);

        return productRepository.save(product);


    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {

        if (mrpPrice <= 0) {

            throw new IllegalArgumentException("Actual price must be greter than 0");
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;


    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {
        ProductEntity product = findProductById(productId);
        productRepository.delete(product);

    }

    @Override
    public ProductEntity updateProduct(Long productId, ProductEntity product) throws ProductException {
        findProductById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public ProductEntity findProductById(Long productId) throws ProductException {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductException("Product not found with id " + productId)
                );

    }

    @Override
    public List<ProductEntity> searchProducts(String query) {
        return productRepository.searchProduct(query);
    }

    @Override
    public Page<ProductEntity> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {


        Specification<ProductEntity> spec = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Filter by category
            if (category != null) {
                Join<ProductEntity, CategoryEntity> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(
                        categoryJoin.get("categoryId"), category));
            }

            // Filter by color
            if (colors != null && !colors.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        root.get("color"), colors));
            }

            // Filter by size
            if (sizes != null && !sizes.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        root.get("size"), sizes));
            }

            // Filter by min price
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("sellingPrice"), minPrice));
            }

            // Filter by max price
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("sellingPrice"), maxPrice));
            }

            // Filter by discount
            if (minDiscount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("discountPercent"), minDiscount));
            }
            if (stock != null) {

                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable;

        if (sort != null && !sort.isEmpty()) {

            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(
                        pageNumber != null ? pageNumber : 0,
                        10,
                        Sort.by("sellingPrice").ascending()
                );
                case "price_high" -> PageRequest.of(
                        pageNumber != null ? pageNumber : 0,
                        10,
                        Sort.by("sellingPrice").descending()
                );
                default -> PageRequest.of(
                        pageNumber != null ? pageNumber : 0,
                        10,
                        Sort.unsorted());
            };

        } else {

            pageable = PageRequest.of(
                    pageNumber != null ? pageNumber : 0,
                    10, Sort.unsorted()
            );
        }


        return productRepository.findAll(spec, pageable);
    }

    @Override
    public List<ProductEntity> getProductBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }
}
