<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Manage reviews">

    <h1>Manage reviews</h1>
    <form method="post">
        <table>
            <thead>
            <tr>
                <th>Product id</th>
                <th>Name</th>
                <th>Rating</th>
                <th>Comment</th>
                <th>Approve</th>
            </tr>
            </thead>
            <c:forEach var="review" items="${reviews}">
                <tr>
                    <td>${review.productId}</td>
                    <td>${review.name}</td>
                    <td>${review.rating}</td>
                    <td>${review.comment}</td>
                    <td>
                        <c:if test="${review.approved}">
                            Approved
                        </c:if>
                        <c:if test="${not review.approved}">
                            <button formaction="${pageContext.request.contextPath}/reviews/approve/${review.id}">Approve</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>

</tags:master>