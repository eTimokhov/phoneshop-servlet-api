<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.cart.Cart" scope="request"/>

<c:if test="${not cart['empty']}">
    <h3>Cart</h3>
    <table>
        <c:forEach var="cartItem" items="${cart.cartItems}">
            <tr>
                <td>
                    <img src="${cartItem.product.imageUrl}" width="40">
                </td>
                <td>${cartItem.quantity}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>Total</td>
            <td>${cart.totalPrice}</td>
        </tr>
    </table>
</c:if>