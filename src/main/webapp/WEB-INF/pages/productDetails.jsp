<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="product" scope="request" class="com.es.phoneshop.model.product.Product" />

<tags:master pageTitle="Product Details">
    <p>${product.description}</p>
    <img src="${product.imageUrl}">
    <p>Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></p>
    <p>Stock: ${product.stock}</p>
    <hr>
    <a href="${pageContext.request.contextPath}/products">Return to product list</a>
</tags:master>