<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Details">
    <h2>Error 404 - Not found</h2>
    <p>${requestScope['javax.servlet.error.message']}</p>
    <hr>
    <a href="${pageContext.request.contextPath}/products">Return to product list</a>
</tags:master>