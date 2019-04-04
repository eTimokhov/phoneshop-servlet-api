<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" class="com.es.phoneshop.model.cart.Cart" scope="request" />

<tags:master pageTitle="Cart">
  <h2>Cart</h2>
  <form method="post">
    <table>
      <thead>
        <tr>
          <td>Image</td>
          <td>Description</td>
          <td>Quantity</td>
          <td class="price">Price</td>
        </tr>
      </thead>
      <c:forEach var="cartItem" items="${cart.cartItems}">
        <c:set var="product" value="${cartItem.product}" />
        <tr>
          <td>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
          </td>
          <td>
            <a href="products/${product.id}">${product.description}</a>
          </td>
          <td>
            <input type="text" value="${cartItem.quantity}" >
          </td>
          <td class="price">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </td>
        </tr>
      </c:forEach>
        <tr>
          <td colspan="3">Total</td>
          <td>${cart.totalPrice}</td>
        </tr>
    </table>
  </form>
</tags:master>