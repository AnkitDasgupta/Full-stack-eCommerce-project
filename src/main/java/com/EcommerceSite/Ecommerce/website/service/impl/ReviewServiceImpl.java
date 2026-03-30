package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.ReviewEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.repository.ReviewRepository;
import com.EcommerceSite.Ecommerce.website.request.CreateReviewRequest;
import com.EcommerceSite.Ecommerce.website.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Override
    public ReviewEntity createReview(CreateReviewRequest req, UserEntity user, ProductEntity product) {
        ReviewEntity review = new ReviewEntity();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRating());
        review.setProductImages(req.getProductImages());

        product.getReviews().add(review);

        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewEntity> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public ReviewEntity updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {

        ReviewEntity review = getReviewById(reviewId);

        if (review.getUser().getId().equals(userId)) {
            review.setReviewText(reviewText);
            review.setRating(rating);
            return reviewRepository.save(review);
        }

        throw new Exception("you can't update this review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        ReviewEntity review = getReviewById(reviewId);

        if (review.getUser().getId().equals(userId)) {
            throw new Exception("you can't delete this review");
        }

        reviewRepository.delete(review);

    }

    @Override
    public ReviewEntity getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("review not found"));
    }
}
