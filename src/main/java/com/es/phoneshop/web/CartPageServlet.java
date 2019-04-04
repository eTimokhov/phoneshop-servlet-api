package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        String[] productIds = request.getParameterValues("id");
        String[] quantities = request.getParameterValues("quantity");
        String[] errors = new String[productIds.length];
        for (int i = 0; i < productIds.length; i++) {
            long productId = Long.parseLong(productIds[i]);
            Integer quantity = parseQuantity(quantities, errors, i);
            if (quantity != null) {
                try {
                    cartService.update(cart, productId, quantity);
                } catch (OutOfStockException | IllegalArgumentException e) {
                    errors[i] = e.getMessage();
                }
            }
        }
        cart.recalculateTotalPrice();

        boolean hasError = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasError) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?message=Updated successfully");
        }
    }

    private Integer parseQuantity(String[] quantities, String[] errors, int index) {
        try {
            return Integer.valueOf(quantities[index]);
        } catch (NumberFormatException e) {
            errors[index] = "Not a number";
        }
        return null;
    }
}
