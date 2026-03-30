package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.ReviewEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService{

    ReviewEntity createReview(CreateReviewRequest req, UserEntity user, ProductEntity product);
    List<ReviewEntity> getReviewByProductId(Long productId);

    ReviewEntity updateReview(Long reviewId,String reviewText,double rating,Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;

    ReviewEntity getReviewById(Long reviewId) throws Exception;
}
