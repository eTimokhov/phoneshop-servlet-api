<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form method="get">
    <input type="text" value="${param.query}" name="query">
    <button type="submit">Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
          <!-- <a href="?query=${param.query}&sortBy=description&order=asc">asc</a> -->
          <tags:sortLink sortBy="description" order="asc" />
          <tags:sortLink sortBy="description" order="desc" />
        </td>
        <td class="price">Price
          <tags:sortLink sortBy="price" order="asc" />
          <tags:sortLink sortBy="price" order="desc" />
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>
          <a href="products/${product.id}">${product.description}</a>
        </td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
  <jsp:include page="/miniCart" />
</tags:master>