<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" class="com.es.phoneshop.model.order.Order" scope="request"/>

<tags:master pageTitle="Overview">

    <h2>Overview</h2>
    <p class="success">Your order #${order.id} has been accepted</p>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Quantity</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
            <c:set var="product" value="${cartItem.product}"/>
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="products/${product.id}">${product.description}</a>
                </td>
                <td>
                    ${cartItem.quantity}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3">Delivery: ${order.deliveryMode.label}</td>
            <td class="price">${order.deliveryMode.cost}</td>
        </tr>
        <tr>
            <td colspan="3">Total</td>
            <td class="price">${order.totalPrice}</td>
        </tr>
    </table>
    <br>

    <p>First name: ${order.firstName}</p>
    <p>Last name: ${order.lastName}</p>
    <p>Phone number: ${order.phoneNumber}</p>
    <p>Delivery mode: ${order.deliveryMode.label}</p>
    <p>Delivery cost: ${order.deliveryCost}</p>
    <p>Delivery date: ${order.deliveryDate}</p>
    <p>Delivery address: ${order.deliveryAddress}</p>
    <p>Payment method: ${order.paymentMethod}</p>

</tags:master>