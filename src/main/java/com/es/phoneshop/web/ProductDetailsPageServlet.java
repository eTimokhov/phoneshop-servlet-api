package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentlyviewed.HttpSessionRecentlyViewedProductsService;
import com.es.phoneshop.model.recentlyviewed.RecentlyViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedProductsService recentlyViewedProductsService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedProductsService = HttpSessionRecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = getProductId(request);
        Product product = productDao.getProduct(productId);

        recentlyViewedProductsService.addProduct(request, product);

        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = getProductId(request);
        String quantityParam = request.getParameter("quantity");
        int quantity;
        try {
            quantity = Integer.parseInt(quantityParam);
        } catch (NumberFormatException e) {
            sendErrorMessage("Not a number", request, response);
            return;
        }

        if (quantity <= 0) {
            sendErrorMessage("Invalid value", request, response);
            return;
        }

        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException e) {
            sendErrorMessage("Not enough stock", request, response);
            return;
        }
        cartService.recalculateTotalPrice(cart);
        String successMessage = "Added to cart successfully";
        response.sendRedirect(request.getRequestURI() + "?message=" + successMessage + "&quantity=" + quantity);
    }

    private long getProductId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        return Long.parseLong(pathInfo.substring(1));
    }

    private void sendErrorMessage(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        doGet(request, response);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public void setRecentlyViewedProductsService(RecentlyViewedProductsService recentlyViewedProductsService) {
        this.recentlyViewedProductsService = recentlyViewedProductsService;
    }
}
