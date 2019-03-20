package com.es.phoneshop.model.product;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();
    private Predicate<Product> isProductCorrect = p -> p.getPrice() != null && p.getStock() > 0;

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter(isProductCorrect)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query) {
        if (query == null || query.trim().isEmpty())
            return findProducts();

        String[] keywords = query.toLowerCase().split(" ");
        ToIntFunction<Product> getNumberOfMatches = product -> (int) Arrays.stream(keywords)
                .filter(product.getDescription().toLowerCase()::contains)
                .count();

        return products.stream()
                .filter(isProductCorrect)
                .filter(product -> Arrays.stream(keywords).anyMatch(product.getDescription().toLowerCase()::contains))
                .sorted(Comparator.comparingInt(getNumberOfMatches).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product != null && products.stream().noneMatch(p -> p.getId().equals(product.getId())))
            products.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}
