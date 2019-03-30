<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="product" required="true" type="com.es.phoneshop.model.product.Product" %>

<div class="last-product-tile">
    <img src="${product.imageUrl}" width="80">
    <br>
    <a href="${pageContext.request.contextPath}/products/${product.id}">${product.description}</a>
    <br>
    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
</div>