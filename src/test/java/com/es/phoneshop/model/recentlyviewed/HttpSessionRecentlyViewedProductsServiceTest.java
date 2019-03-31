package com.es.phoneshop.model.recentlyviewed;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionRecentlyViewedProductsServiceTest {

    private final String SESSION_RECENTLY_VIEWED_KEY = "recentlyViewedProducts";

    private HttpSessionRecentlyViewedProductsService service = HttpSessionRecentlyViewedProductsService.getInstance();

    @Mock
    private HttpServletRequest existingSessionRequest;
    @Mock
    private HttpSession existingSession;
    @Mock
    private HttpServletRequest newRequest;
    @Mock
    private HttpSession newSession;

    private LinkedList<Product> products;

    @Before
    public void setup() {
        service.setMaxProductCount(3);
        when(newRequest.getSession()).thenReturn(newSession);
        products = new LinkedList<>();
        when(newSession.getAttribute(SESSION_RECENTLY_VIEWED_KEY)).thenReturn(products);
    }

    @Test
    public void testGetListReturnExistingList() {
        LinkedList<Product> existingProductList = new LinkedList<>();
        when(existingSessionRequest.getSession()).thenReturn(existingSession);
        when(existingSession.getAttribute(SESSION_RECENTLY_VIEWED_KEY)).thenReturn(existingProductList);

        LinkedList<Product> productList = service.getProducts(existingSessionRequest);
        assertEquals(existingProductList, productList);
    }

    @Test
    public void testAddSameProductMultipleTimes() {
        Product product = new Product();
        service.addProduct(newRequest, product);
        service.addProduct(newRequest, product);
        assertEquals(1, products.size());
    }

    @Test
    public void testAddMoreProductsThanMaxCount() {
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();

        service.addProduct(newRequest, product1);
        service.addProduct(newRequest, product2);
        service.addProduct(newRequest, product3);
        service.addProduct(newRequest, product4);

        assertEquals(3, products.size());
    }

    @Test
    public void testLastAddedProductBecomesFirstInList() {
        Product product1 = new Product();
        Product product2 = new Product();
        ;
        service.addProduct(newRequest, product1);
        service.addProduct(newRequest, product2);
        service.addProduct(newRequest, product1);

        assertEquals(product1, products.getFirst());
    }

}