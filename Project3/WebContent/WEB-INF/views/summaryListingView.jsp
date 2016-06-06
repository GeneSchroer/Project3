<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Produce Summary Listing of Revenue</title>
</head>
<body>
<p style="color:red;">${errorString}</p>
<form method="POST" action="doSummaryListing">
	By Stock:
	<select name="stockSymbol">
		<option value = "null">None</option>
		<c:forEach items="${stockList}" var="stock">
			<option value="${stock.stockSymbol}">${stock.stockSymbol} ${stock.companyName}</option>
		</c:forEach>
	</select>
	<br>
	By Stock Type:
	<select name="stockType">
		<option value = "null">None</option>
		<c:forEach items ="${stockTypeList}" var="string">
		<option value="${string}">${string}</option>
		
		</c:forEach>
	
	</select>

First Name:
<p style="color: red;">${errorStrFirstName}</p>
<input type="text" name="firstName" value="${firstName}"/><br>
Last Name:
<p style="color: red;">${errorStrLastName}</p>
<input type="text" name="lastName" value="${lastName}"/><br>

<input type="submit" value="Submit"/><br>

</form>

<c:if test="${not empty orderList}">
	<table border="2" cellpadding="5" cellspacing="1">
	<tr>
		<th>Order Id</th>
		<th>Stock</th>
		<th>Account Id</th>
		<th>Broker Id</th>
		<th>Order Type</th>
		<th># of Shares</th>
		<th>Order placed</th>
		<th>Order executed</th>
		<th>Price Per Share</th>
		<th>Price Type</th>

		</tr>
	<c:forEach items="${orderList}" var="fullOrder">
	<tr>
		<td>${fullOrder.id}</td>
		<td>${fullOrder.stockId}</td>
		<td>${fullOrder.accountId}</td>
		<td>${fullOrder.brokerId}</td>
		<td>${fullOrder.orderType}</td>
		<td>${fullOrder.numShares}</td>
		<td>${fullOrder.dateTime}
		<td>${fullOrder.finalDateTime}</td>
		<td>${fullOrder.finalPricePerShare}
		<td>${fullOrder.priceType}</td>
				
	</tr>
	</c:forEach>
	</table>
</c:if>
<br>
<a href="${pageContext.request.contextPath}/manager">Return to manager page</a>
</body>
</html>