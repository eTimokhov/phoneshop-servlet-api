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
    <b><%= HttpSessionCartService.getInstance().getCart(request) %></b>
  </p>
  <main>
    <jsp:doBody/>
  </main>
</body>
</html>