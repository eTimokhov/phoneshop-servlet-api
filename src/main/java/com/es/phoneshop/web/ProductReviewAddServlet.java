package com.es.phoneshop.web;

import com.es.phoneshop.model.review.ProductReview;
import com.es.phoneshop.model.review.ProductReviewService;
import com.es.phoneshop.model.review.ProductReviewServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductReviewAddServlet extends HttpServlet {

    private ProductReviewService productReviewService;

    @Override
    public void init() {
        productReviewService = ProductReviewServiceImpl.getInstance();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String ratingString = request.getParameter("rating");
        int rating = Integer.parseInt(ratingString);
        //TODO: add correct rating parsing
        String comment = request.getParameter("comment");
        long productId = getProductId(request);
        ProductReview review = productReviewService.getProductReview(productId);
        review.setName(name);
        review.setRating(rating);
        review.setComment(comment);
        productReviewService.addProductReview(review);

        response.sendRedirect( request.getContextPath() + "/products/" + productId + "?message=Review added successfully");; //TODO: change path
    }

    private long getProductId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String productIdString = pathInfo.substring(1);
        return Long.parseLong(productIdString);
    }
}
