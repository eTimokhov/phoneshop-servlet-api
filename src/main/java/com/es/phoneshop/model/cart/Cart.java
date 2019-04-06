package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private final List<CartItem> cartItems;
    private BigDecimal totalPrice;

    public Cart() {
        cartItems = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                '}';
    }
}
