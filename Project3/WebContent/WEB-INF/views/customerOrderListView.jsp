<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee List</title>
</head>
<body>

<table border="2" cellpadding="5" cellspacing="1">
	<tr>
		<th>Id</th>
		<th>NumShares</th>
		<th>Date</th>
		<th>PriceType</th>
		<th>OrderType</th>
		<th>View Details</th>
	</tr>
	<c:forEach items="${orderList}" var="orders">
		<tr>
			<td>${orders.id}</td>
			<td>${orders.numShares}</td>
			<td>${orders.dateTime}</td>
			<td>${orders.priceType}</td>
			<td>${orders.orderType}</td>
			<td><a href="orderDetails?id=${orders.id}">Details</a></td>
		</tr>
	
	</c:forEach>

</table>
<a href="${pageContext.request.contextPath}/createOrder">Place Order</a>
<a href="${pageContext.request.contextPath}/customer">Return to customer menu</a>

</body>
</html>