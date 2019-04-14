package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private static OrderServiceImpl instance;

    private OrderServiceImpl(){
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Order createOrder(Cart cart, DeliveryMode deliveryMode) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList()));
        order.setDeliveryMode(deliveryMode);
        order.setDeliveryCost(deliveryMode.getCost());
        order.setSubTotalPrice(cart.getTotalPrice());
        order.setTotalPrice(cart.getTotalPrice().add(deliveryMode.getCost()));
        return order;
    }

    @Override
    public List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.save(order);
    }
}
