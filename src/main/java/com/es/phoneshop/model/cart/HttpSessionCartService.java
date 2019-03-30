package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;

import java.util.Optional;

public class HttpSessionCartService implements CartService {

    private static HttpSessionCartService instance;
    private ProductDao productDao;
    private Cart cart = new Cart();

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
    public void add(long productId, int quantity) throws ProductNotFoundException, OutOfStockException {
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

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
