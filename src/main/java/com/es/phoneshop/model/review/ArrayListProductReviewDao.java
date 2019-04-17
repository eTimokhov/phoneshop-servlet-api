package com.es.phoneshop.model.review;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ArrayListProductReviewDao implements ProductReviewDao {

    private static ArrayListProductReviewDao instance;

    private List<ProductReview> reviews = new ArrayList<>();
    private final AtomicLong currentId = new AtomicLong(0);

    public static ArrayListProductReviewDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductReviewDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductReviewDao();
                }
            }
        }
        return instance;
    }

    private ArrayListProductReviewDao() {
    }


    @Override
    public void save(ProductReview review) {
        review.setId(currentId.incrementAndGet());
        reviews.add(review);
    }

    @Override
    public List<ProductReview> getReviews(long productId) {
        return reviews.stream()
                .filter(p -> p.getProductId() == productId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReview> getReviews() {
        return reviews;
    }

    @Override
    public ProductReview getReview(long reviewId) {
        return reviews.stream()
                .filter(p -> p.getId() == reviewId)
                .findAny()
                .orElse(null);
    }
}
