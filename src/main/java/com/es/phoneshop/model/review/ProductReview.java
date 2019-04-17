package com.es.phoneshop.model.review;

import java.io.Serializable;

public class ProductReview implements Serializable {
    private long id;
    private long productId;
    private String name;
    private int rating;
    private String comment;

    private boolean approved;

    public ProductReview() {
    }

    public ProductReview(long productId, String name, int rating, String comment) {
        this.productId = productId;
        this.name = name;
        this.rating = rating;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
