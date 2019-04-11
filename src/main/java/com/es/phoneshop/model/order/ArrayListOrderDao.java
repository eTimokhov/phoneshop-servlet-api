package com.es.phoneshop.model.order;

import java.util.List;

public class ArrayListOrderDao implements OrderDao {

    private static ArrayListOrderDao instance;
    private List<Order> orders;

    private ArrayListOrderDao() {
    }

    public OrderDao getInstance() {
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
        orders.add(order);
    }
}
