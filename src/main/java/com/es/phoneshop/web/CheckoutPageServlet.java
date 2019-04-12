package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        String deliveryModeString = request.getParameter("deliveryMode");
        Optional<String> optionalDeliveryModeString = Optional.ofNullable(deliveryModeString);
        DeliveryMode deliveryMode =
                DeliveryMode.valueOf(optionalDeliveryModeString.orElse(DeliveryMode.STORE_PICKUP.toString()));

        Order order = orderService.createOrder(cart, deliveryMode);
        List<DeliveryMode> deliveryModes = orderService.getDeliveryModes();

        request.setAttribute("order", order);
        request.setAttribute("deliveryModes", deliveryModes);
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String deliveryModeString = request.getParameter("deliveryMode");
        String deliveryDateString = request.getParameter("deliveryDate");
        String deliveryAddress = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");

        DeliveryMode deliveryMode = null;
        Date deliveryDate = null;

        boolean hasError = Stream
                .of(firstName, lastName, phoneNumber, deliveryModeString, deliveryDateString, deliveryAddress, paymentMethod)
                .anyMatch(String::isEmpty);
        try {
            deliveryMode = DeliveryMode.valueOf(deliveryModeString);
            deliveryDate = parseDate(deliveryDateString);
        } catch (IllegalArgumentException | ParseException e) {
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("error", "Invalid data");
            doGet(request, response);
            return;
        }

        Order order = orderService.createOrder(cartService.getCart(request), deliveryMode);
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setPhoneNumber(phoneNumber);
        order.setDeliveryDate(deliveryDate);
        order.setDeliveryAddress(deliveryAddress);
        order.setPaymentMethod(paymentMethod);

        orderService.placeOrder(order);

        Cart cart = cartService.getCart(request);
        cartService.clear(cart);

        String secureId = order.getSecureId();
        response.sendRedirect(request.getContextPath() + "/order/overview/" + secureId);
    }

    private Date parseDate(String deliveryDateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);
        return dateFormat.parse(deliveryDateString);
    }
}
