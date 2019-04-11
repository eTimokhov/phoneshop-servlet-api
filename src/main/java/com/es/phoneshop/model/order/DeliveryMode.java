package com.es.phoneshop.model.order;

import java.math.BigDecimal;

public enum DeliveryMode {
    COURIER("Courier", BigDecimal.valueOf(10)),
    STORE_PICKUP("Store Pickup", BigDecimal.ZERO);

    private final String label;
    private final BigDecimal cost;

    DeliveryMode(String label, BigDecimal cost) {
        this.label = label;
        this.cost = cost;
    }

    public String getLabel() {
        return label;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
