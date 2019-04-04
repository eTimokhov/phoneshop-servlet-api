package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long productId = getProductId(request);
        Cart cart = cartService.getCart(request);
        cartService.delete(cart, productId);
        cartService.recalculateTotalPrice(cart);

        response.sendRedirect(request.getContextPath() + "/cart?message=Deleted successfully");
    }

    private long getProductId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int servletPathIndex = uri.indexOf(request.getServletPath());
        return Long.parseLong(uri.substring(servletPathIndex + request.getServletPath().length() + 1));
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
