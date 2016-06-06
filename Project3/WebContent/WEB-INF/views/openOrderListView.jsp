<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of Open Orders</title>
</head>
<body>
	<table border="2" cellpadding="5" cellspacing="1">
	<tr>
		<th>Order Id</th>
		<th>Account Id</th>
		<th>Number of Shares</th>
		<th>Price Per Share</th>
		<th>Date Time</th>
		<th>Order Type</th>
		<th>Price Type</th>
		<th>Percentage</th>
		<th>Complete Order</th>
	</tr>
	<c:forEach items="${openOrderList}" var="openOrder">
		<tr>
			<td>${openOrder.id}</td>
			<td>${openOrder.accountId}</td>
			<td>${openOrder.stockId}</td>
			<td>${openOrder.numShares}</td>
			<td>${openOrder.dateTime}</td>
			<td>${openOrder.orderType}</td>
			<td>${openOrder.priceType}</td>
			<td>${openOrder.percentage}</td>
			<td><a href="completeOrder?id=${openOrder.id}">Complete</a></td>
		</tr>
	
	</c:forEach>
	</table>
	 <a href="${pageContext.request.contextPath}/recordOrder">Record New Order</a>
<a href="${pageContext.request.contextPath}/representative">Return to representative view</a>
</body>
</html>