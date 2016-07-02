<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Suggestions</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

<h3>Stock Suggestions:</h3>
	<table border="1" cellpadding="1" cellspacing="1">
	<tr>
		<th>Symbol:</th>
		<th>Name:</th>
		<th>Type:</th>
		<th>Price Per Share:</th>
	</tr>
	<c:forEach items="${stockList}" var="stock">
		<tr>
			<td>${stock.stockSymbol}</td>
			<td>${stock.companyName}</td>
			<td>${stock.type}</td>
			<td>${stock.pricePerShare}</td>
		</tr>
	</c:forEach>
	</table>

	<a class="returnbtn" href="${pageContext.request.contextPath}/customers">Return to customers page</a>


</body>
</html>