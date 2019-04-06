package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {

    private static HttpSessionCartService instance;
    private static final String SESSION_CART_KEY = "sessionCart";
    private ProductDao productDao;

    private HttpSessionCartService(){
        productDao = ArrayListProductDao.getInstance();
    }

    public static HttpSessionCartService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                if (instance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException {
        Product product = productDao.getProduct(productId);
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(c -> c.getProduct().getId() == productId)
                .findAny();

        int currentQuantity = optionalCartItem.map(CartItem::getQuantity).orElse(0);
        if (currentQuantity + quantity > product.getStock()) {
            throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
        }

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }

        recalculateTotalPrice(cart);
    }

    @Override
    public void update(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(c -> c.getProduct().getId() == productId)
                .findAny();

        Product product;
        if (optionalCartItem.isPresent()) {
            product = optionalCartItem.get().getProduct();
        } else {
            product = productDao.getProduct(productId);
        }

        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
        }

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(quantity);
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }

        recalculateTotalPrice(cart);
    }

    @Override
    public void delete(Cart cart, long productId) {
        cart.getCartItems().removeIf(c -> c.getProduct().getId().equals(productId));
        recalculateTotalPrice(cart);
    }

    private void recalculateTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(c -> c.getProduct().getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        cart.setTotalPrice(totalPrice);
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
