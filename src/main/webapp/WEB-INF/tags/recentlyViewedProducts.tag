<%@ tag import="com.es.phoneshop.model.cart.HttpSessionCartService" %>
<%@ tag import="com.es.phoneshop.model.recentlyviewed.HttpSessionRecentlyViewedProductsService" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="products" value="${sessionScope.recentlyViewedProducts}"/>

<h2>Recently viewed</h2>
<c:if test="${not empty products}">
  <c:forEach var="product" items="${products}">
    <tags:productTile product="${product}" />
  </c:forEach>
</c:if>