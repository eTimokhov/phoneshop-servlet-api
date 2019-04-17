package com.es.phoneshop.web;

import com.es.phoneshop.model.review.ProductReviewService;
import com.es.phoneshop.model.review.ProductReviewServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductReviewApproveServlet extends HttpServlet {

    private ProductReviewService productReviewService;

    @Override
    public void init() {
        productReviewService = ProductReviewServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long reviewId = getReviewId(request);
        productReviewService.approve(reviewId);
        response.sendRedirect( request.getContextPath() + "/reviews");
    }

    private long getReviewId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String reviewIdString = pathInfo.substring(1);
        return Long.parseLong(reviewIdString);
    }
}
