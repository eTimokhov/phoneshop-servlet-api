package com.es.phoneshop.model.review;

import java.util.List;

public class ProductReviewServiceImpl implements ProductReviewService {

    private static ProductReviewServiceImpl instance;
    private ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();

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
    public ProductReview getProductReview(long productId) {
        ProductReview productReview = new ProductReview();
        productReview.setProductId(productId);
        return productReview;
    }

    @Override
    public void addProductReview(ProductReview review) {
        productReviewDao.save(review);
    }
}
