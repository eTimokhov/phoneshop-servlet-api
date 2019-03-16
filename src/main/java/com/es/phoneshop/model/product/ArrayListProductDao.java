package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) {
        Optional<Product> product = products.stream().filter((p) -> p.getId().equals(id)).findFirst();
        return product.orElse(null);
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream().filter((p) -> p.getPrice() != null && p.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product != null && products.stream().noneMatch((p) -> p.getId().equals(product.getId())))
            products.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf((p) -> p.getId().equals(id));
    }
}
