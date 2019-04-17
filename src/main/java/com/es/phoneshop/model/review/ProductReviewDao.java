package com.es.phoneshop.model.review;

import java.util.List;

public interface ProductReviewDao {
    void save(ProductReview review);
    List<ProductReview> getReviews(long productId);
}
