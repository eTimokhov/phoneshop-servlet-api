<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.es.phoneshop.model.recentlyviewed.HttpSessionRecentlyViewedProductsService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="products" value="${sessionScope.recentlyViewedProducts}"/>
<h2>Recently viewed</h2>
<c:if test="${not empty products}">
    <c:forEach var="product" items="${products}">
        <tags:productTile product="${product}" />
    </c:forEach>
</c:if>

