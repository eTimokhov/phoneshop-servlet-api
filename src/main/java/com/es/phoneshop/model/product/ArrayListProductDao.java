package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao instance;

    private List<Product> products = new ArrayList<>();
    private final Predicate<Product> isProductCorrect = p -> p.getPrice() != null && p.getStock() > 0;

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private ArrayListProductDao() {
    }

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
        if (query == null || query.trim().isEmpty()) {
            return findProducts();
        }

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
    public synchronized List<Product> findProducts(String query, String sortBy, boolean ascending) {
        List<Product> products = findProducts(query);
        if (sortBy == null) {
            return products;
        }

        Comparator<Product> productComparator = null;
        switch (sortBy) {
            case "description":
                productComparator = Comparator.comparing(Product::getDescription, Comparator.comparing(String::toLowerCase));
                break;
            case "price":
                productComparator = Comparator.comparing(Product::getPrice);
                break;
        }
        if (productComparator == null) {
            return products;
        }

        if (!ascending) {
            productComparator = productComparator.reversed();
        }

        products.sort(productComparator);
        return products;
    }

    @Override
    public synchronized void save(Product product) {
        if (product != null && products.stream().noneMatch(p -> p.getId().equals(product.getId()))) {
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    public synchronized void setProducts(List<Product> products) {
        this.products = products;
    }
}
