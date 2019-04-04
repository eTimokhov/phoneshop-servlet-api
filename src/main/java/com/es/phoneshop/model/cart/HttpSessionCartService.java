package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    }

    @Override
    public void update(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        Product product = productDao.getProduct(productId);
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(c -> c.getProduct().getId() == productId)
                .findAny();

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
