<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Search</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Stock Search</h3>	
	<c:choose>
		<c:when test="${not empty stockList}">
			<table border="2">
					<tr>
						<th>Stock Symbol</th>
						<th>Company Name</th>
						<th>Type</th>
						<th>Price Per Share</th>
						<th>Shares Available</th>
						<th>Recent Orders</th>
					</tr>
				<c:forEach items="${stockList}" var="fullStock">
					<tr>
						<td>${fullStock.stockSymbol}</td>
						<td>${fullStock.companyName}</td>
						<td>${fullStock.type}</td>
						<td>${fullStock.pricePerShare}</td>
						<td>${fullStock.numShares}</td>
						<td>
						<a href = "${pageContext.request.contextPath}/customers/recentOrders?stockId=${fullStock.stockSymbol}">Orders</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p>No stocks were found</p>
		</c:otherwise>
		  
	</c:choose>
	<a href = "${pageContext.request.contextPath}/customers">Return to main page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>