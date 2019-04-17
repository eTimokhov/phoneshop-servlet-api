package com.es.phoneshop.model.review;

import java.util.List;
import java.util.stream.Collectors;

public class ProductReviewServiceImpl implements ProductReviewService {

    private static ProductReviewServiceImpl instance;
    private final ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();

    public static ProductReviewServiceImpl getInstance() {
        if (instance == null) {
            synchronized (ProductReviewServiceImpl.class) {
                if (instance == null) {
                    instance = new ProductReviewServiceImpl();
                }
            }
        }
        return instance;
    }

    private ProductReviewServiceImpl() {
    }

    @Override
    public List<ProductReview> getProductReviews(long productId) {
        return productReviewDao.getReviews(productId);
    }

    @Override
    public List<ProductReview> getProductReviews() {
        return productReviewDao.getReviews();
    }

    @Override
    public List<ProductReview> getApprovedProductReviews(long productId) {
        return getProductReviews(productId).stream()
                .filter(ProductReview::isApproved)
                .collect(Collectors.toList());
    }

    @Override
    public ProductReview getProductReview(long productId) {
        ProductReview productReview = new ProductReview();
        productReview.setProductId(productId);
        return productReview;
    }

    @Override
    public void addProductReview(ProductReview review) {
        productReviewDao.save(review);
    }

    @Override
    public void approve(long productReviewId) {
        ProductReview review = productReviewDao.getReview(productReviewId);
        review.setApproved(true);
    }
}
