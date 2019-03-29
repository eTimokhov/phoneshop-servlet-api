package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private static ArrayListProductDao productDao;

    @BeforeClass
    public static void initProductDao() {
        productDao = ArrayListProductDao.getInstance();
    }

    @After
    public void clear() {
        productDao.setProducts(new ArrayList<>());
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithResults() {
        Product product = getCorrectProduct();
        product.setId(1L);
        productDao.save(product);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testGetProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        productDao.save(product);
        assertNotNull(productDao.getProduct(1L));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        productDao.save(product);
        productDao.delete(1L);
        productDao.getProduct(1L);
    }

    @Test
    public void testAddTwoProductsWithSameId() {
        Product product1 = getCorrectProduct();
        product1.setId(1L);
        productDao.save(product1);
        Product product2 = getCorrectProduct();
        product2.setId(1L);
        productDao.save(product2);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testFindProductsWithZeroStock() {
        Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        productDao.save(product);
        assertEquals(0, productDao.findProducts().size());
    }

    @Test
    public void testFindProductsWithNullPrice() {
        Product product = new Product();
        product.setStock(1);
        productDao.save(product);
        assertEquals(0, productDao.findProducts().size());
    }

    @Test
    public void testSearchProducts() {
        Product p1 = getCorrectProduct();
        Product p2 = getCorrectProduct();
        Product p3 = getCorrectProduct();
        p1.setId(1L);
        p2.setId(2L);
        p3.setId(3L);
        p1.setDescription("Samsung Galaxy S II");
        p2.setDescription("Siemens C56");
        p3.setDescription("iPhone X");
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);

        List<Product> result1 = productDao.findProducts("samsung s II");
        assertTrue(result1.contains(p1));
        assertTrue(result1.contains(p2));
        assertTrue(!result1.contains(p3));

        List<Product> result2 = productDao.findProducts("iphone 6");
        assertTrue(!result2.contains(p1));
        assertTrue(result2.contains(p2));
        assertTrue(result2.contains(p3));

    }

    public void testSearchProductsWithEmptyQuery() {
        Product p1 = getCorrectProduct();
        Product p2 = getCorrectProduct();
        Product p3 = getCorrectProduct();
        p1.setId(1L);
        p2.setId(2L);
        p3.setId(3L);
        p1.setDescription("Samsung Galaxy S II");
        p2.setDescription("Siemens C56");
        p3.setDescription("iPhone X");
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);

        assertEquals(3, productDao.findProducts(null).size());
        assertEquals(3, productDao.findProducts("   ").size());
        assertEquals(3, productDao.findProducts("").size());
    }

    @Test
    public void testSearchProductsOrder() {
        Product p1 = getCorrectProduct();
        Product p2 = getCorrectProduct();
        Product p3 = getCorrectProduct();
        p1.setId(1L);
        p2.setId(2L);
        p3.setId(3L);
        p1.setDescription("Samsung Galaxy S II");
        p2.setDescription("Samsung Galaxy S III");
        p3.setDescription("Samsung Galaxy Note 9");
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);

        assertEquals(p1, productDao.findProducts("samsung ii").get(0));
        assertEquals(p2, productDao.findProducts("galaxy s iii").get(0));
        assertEquals(p3, productDao.findProducts("galaxy 9").get(0));

    }

    @Test
    public void testSortProducts() {
        Product p1 = getCorrectProduct();
        Product p2 = getCorrectProduct();
        Product p3 = getCorrectProduct();
        p1.setId(1L);
        p2.setId(2L);
        p3.setId(3L);
        p1.setDescription("Samsung Galaxy S II");
        p2.setDescription("Siemens C56");
        p3.setDescription("iPhone X");
        p1.setPrice(BigDecimal.valueOf(5));
        p2.setPrice(BigDecimal.valueOf(10));
        p3.setPrice(BigDecimal.valueOf(3));
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);

        List<Product> result2 = productDao.findProducts(null, "price", false);
        assertEquals(p2, result2.get(0));
        assertEquals(p1, result2.get(1));
        assertEquals(p3, result2.get(2));

        List<Product> result1 = productDao.findProducts(null, "description", true);
        assertEquals(p3, result1.get(0));
        assertEquals(p1, result1.get(1));
        assertEquals(p2, result1.get(2));
    }

    private Product getCorrectProduct() {
        Product product = new Product();
        product.setStock(1);
        product.setPrice(BigDecimal.ONE);
        return product;
    }
}
