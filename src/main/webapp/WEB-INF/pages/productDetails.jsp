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

    <form method="post" action="${pageContext.request.contextPath}/products/${product.id}">
        <label>
            Quantity: <input type="text" name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
        </label>
        <input type="submit" value="Add to cart">
        <br>
        <c:if test="${not empty error}">
            <span class="error">${error}</span>
        </c:if>
        <c:if test="${not empty param.message}">
            <span class="success">${param.message}</span>
        </c:if>
    </form>

    <h3>Reviews</h3>
    <c:if test="${empty reviews}">
        <p>There are no reviews yet</p>
    </c:if>
    <c:if test="${not empty reviews}">
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Rating</th>
                    <th>Comment</th>
                </tr>
            </thead>
            <c:forEach var="review" items="${reviews}">
                <tr>
                    <td>${review.name}</td>
                    <td>${review.rating}</td>
                    <td>${review.comment}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br><br>

    <h3>Add review</h3>
    <form method="post" action="${pageContext.request.contextPath}/reviews/add/${product.id}">
        <label for="name">Your name:</label>
        <input type="text" id="name" name="name"><br><br>

        <label for="rating">Rating (1-5):</label>
        <input type="text" id="rating" name="rating"><br><br>

        <label for="comment">Comment:</label>
        <textarea id="comment" name="comment"></textarea><br><br>

        <input type="submit">
    </form>

    <jsp:include page="/miniCart" />
    <hr>
    <a href="${pageContext.request.contextPath}/products">Return to product list</a>
</tags:master>