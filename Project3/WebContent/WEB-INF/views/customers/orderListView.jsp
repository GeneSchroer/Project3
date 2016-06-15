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
		<th>Date Placed</th>
		<th>Date Executed</th>
		<th>Price Per Share</th>
		<th>PriceType</th>
		<th>OrderType</th>
		<th>View Details</th>
	</tr>
	<c:forEach items="${fullOrderList}" var="fullOrder">
		<tr>
			<td>${fullOrder.id}</td>
			<td>${fullOrder.numShares}</td>
			<td>${fullOrder.dateTime}</td>
			<td><c:if test="${not empty fullOrder.finalDateTime}">${fullOrder.finalDateTime}</c:if></td>
			<td><c:if test="${not empty fullOrder.finalPricePerShare}">${fullOrder.finalPricePerShare}</c:if></td>
			<td>${fullOrder.priceType}</td>
			<td>${fullOrder.orderType}</td>
			<td>
			<!-- If order is trailing stop -->
			<c:choose>
			
			<c:when test="${fullOrder.priceType eq 'Trailing Stop'}">
			<a href="trailingStopHistory?id=${fullOrder.id}&tId=${fullOrder.transactionId}">Details</a>
			</c:when>
			<c:when test="${fullOrder.priceType eq 'Hidden Stop'}">
			<a href="hiddenStopHistory?id=${fullOrder.id}&tId=${fullOrder.transactionId}&sId=${fullOrder.stockId}">Details</a>
			</c:when>
			<c:otherwise>
			<a href="orderDetails?id=${fullOrder.id}">Details</a>
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
	
	</c:forEach>

</table>
	<a href="${pageContext.request.contextPath}/customers/orderList/placeOrder">Place an order</a><br>
	<a href="${pageContext.request.contextPath}/customers">Return to customer menu</a>

</body>
</html>