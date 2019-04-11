package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArrayListOrderDao implements OrderDao {

    private static ArrayListOrderDao instance;
    private final AtomicLong currentId = new AtomicLong(0);
    private final List<Order> orders;

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
    }

    public static OrderDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    @Override
    public void save(Order order) {
        order.setId(currentId.incrementAndGet());
        order.setSecureId(UUID.randomUUID().toString());
        orders.add(order);
    }

    @Override
    public Order getBySecureId(String secureId) {
        return orders.stream()
                .filter(o -> o.getSecureId().equals(secureId))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }
}
