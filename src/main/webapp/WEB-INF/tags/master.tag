<%@ tag import="com.es.phoneshop.model.cart.HttpSessionCartService" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
  <header>
    <a href="${pageContext.servletContext.contextPath}">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a>
  </header>
  <p>
    <b>${sessionScope.sessionCart}</b>
  </p>
  <main>
    <jsp:doBody/>
    <jsp:include page="../pages/recentlyViewedProducs.jsp" />
  </main>
</body>
</html>