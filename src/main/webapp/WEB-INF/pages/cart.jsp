<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" class="com.es.phoneshop.model.cart.Cart" scope="request" />

<tags:master pageTitle="Cart">
  <h2>Cart</h2>
  <p class="success">${param.message}</p>
  <c:if test="${not empty requestScope.errors}">
    <p class="error">Error occurred while updating cart</p>
  </c:if>
  <form method="post" action="${pageContext.request.contextPath}/cart">
    <table>
      <thead>
        <tr>
          <td>Image</td>
          <td>Description</td>
          <td>Quantity</td>
          <td class="price">Price</td>
          <td>Actions</td>
        </tr>
      </thead>
      <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
        <c:set var="product" value="${cartItem.product}" />
        <tr>
          <td>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
          </td>
          <td>
            <a href="products/${product.id}">${product.description}</a>
          </td>
          <td>
            <input type="text" name="quantity"
                   value="${not empty paramValues.quantity[status.index] ? paramValues.quantity[status.index] : cartItem.quantity}" >
            <c:if test="${not empty requestScope.errors[status.index]}">
              <br>
              <span class="error">${requestScope.errors[status.index]}</span>
            </c:if>
            <input type="hidden" name="id" value="${product.id}">
          </td>
          <td class="price">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </td>
          <td>
            <button formaction="cart/deleteCartItem/${product.id}">Delete</button>
          </td>
        </tr>
      </c:forEach>
        <tr>
          <td colspan="4">Total</td>
          <td class="price">${cart.totalPrice}</td>
        </tr>
    </table>
    <input type="submit"  value="Update">
  </form>

  <form action="${pageContext.request.contextPath}/checkout">
    <input type="submit" value="Checkout">
  </form>

</tags:master>