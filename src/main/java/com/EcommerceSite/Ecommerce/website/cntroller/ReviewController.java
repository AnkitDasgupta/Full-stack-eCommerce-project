package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.ReviewEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.ProductException;
import com.EcommerceSite.Ecommerce.website.request.CreateReviewRequest;
import com.EcommerceSite.Ecommerce.website.responseDto.ApiResponse;
import com.EcommerceSite.Ecommerce.website.service.ProductService;
import com.EcommerceSite.Ecommerce.website.service.ReviewService;
import com.EcommerceSite.Ecommerce.website.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewEntity>> getReviewsByProductId(
            @PathVariable Long productId) {

        List<ReviewEntity> reviews = reviewService.getReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewEntity> writeReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);
        ProductEntity product = productService.findProductById(productId);

        ReviewEntity review = reviewService.createReview(
                req, user, product
        );
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewEntity> updateReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        ReviewEntity review = reviewService.updateReview(
                reviewId,
                req.getReviewText(),
                req.getReviewRating(),
                user.getId()
        );

        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        reviewService.deleteReview(reviewId, user.getId());

        ApiResponse res = new ApiResponse();
        res.setMessage("Review deleted successfully");


        return ResponseEntity.ok(res);
    }
}
