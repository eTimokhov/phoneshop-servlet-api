<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" class="com.es.phoneshop.model.order.Order" scope="request"/>

<tags:master pageTitle="Checkout">
    <c:if test="${not empty requestScope.error}">
        <span class="error">${requestScope.error}</span>
    </c:if>
    <c:if test="${order.cartItems.size() ne 0}" >
        <h2>Checkout</h2>
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
                <td colspan="3">Subtotal</td>
                <td class="price">${order.subTotalPrice}</td>
            </tr>
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

        <form method="post" action="${pageContext.request.contextPath}/checkout">
            <label for="firstName">First name:</label>
            <input type="text" id="firstName" name="firstName"
                   value="${param.firstName}" required><br><br>

            <label for="lastName">Last name:</label>
            <input type="text" id="lastName" name="lastName"
                   value="${param.lastName}" required><br><br>

            <label for="phoneNumber">Phone number:</label>
            <input type="tel" id="phoneNumber" name="phoneNumber"
                   value="${param.phoneNumber}" required><br><br>

            <label for="deliveryMode">Delivery mode:</label>
            <select id="deliveryMode" name="deliveryMode" onchange="changeDeliveryModeButton.click()">
                <c:forEach var="mode" items="${deliveryModes}">
                    <option value="${mode}" ${order.deliveryMode eq mode ? "selected" : ""}>${mode.label}</option>
                </c:forEach>
            </select><br><br>

            <button id="changeDeliveryModeButton" formaction="checkout" formmethod="get" formnovalidate
                    style="display: none"></button>

            <label for="deliveryCost">Delivery cost:</label>
            <input type="text" id="deliveryCost" name="deliveryCost"
                   value="${order.deliveryMode.cost}" readonly><br><br>

            <label for="deliveryDate">Delivery date:</label>
            <input type="date" id="deliveryDate" name="deliveryDate"
                   value="${param.deliveryDate}" required><br><br>

            <label for="address">Delivery address:</label>
            <input type="text" id="address" name="address"
                   value="${param.address}" required><br><br>

            <label for="paymentMethod">Payment method:</label>
            <select id="paymentMethod" name="paymentMethod">
                <option ${param.paymentMethod eq "Money" ? "selected" : ""}>Money</option>
                <option ${param.paymentMethod eq "Credit card" ? "selected" : ""}>Credit card</option>
            </select><br><br>

            <input type="submit" value="Order">

        </form>
    </c:if>

</tags:master>