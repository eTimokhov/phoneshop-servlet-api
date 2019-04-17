package com.es.phoneshop.model.review;

import java.util.List;

public interface ProductReviewService {

    List<ProductReview> getProductReviews(long productId);
    ProductReview getProductReview(long productId);
    void addProductReview(ProductReview review);

}
