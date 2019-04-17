package com.es.phoneshop.web;

import com.es.phoneshop.model.review.ProductReview;
import com.es.phoneshop.model.review.ProductReviewService;
import com.es.phoneshop.model.review.ProductReviewServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductReviewsPageServlet extends HttpServlet {

    private ProductReviewService productReviewService;

    @Override
    public void init() {
        productReviewService = ProductReviewServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductReview> productReviewList = productReviewService.getProductReviews();
        request.setAttribute("reviews",productReviewList);
        request.getRequestDispatcher("/WEB-INF/pages/reviews.jsp").forward(request, response);
    }


}
