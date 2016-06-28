<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Transactions</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>


<h1>Customer Transactions</h1>
	
	<br>
	<a href="${pageContext.request.contextPath}/customers/stockHolding">View your stock portfolio</a><br>
	<a href="${pageContext.request.contextPath}/customers/stockHistory">Look at history of a stock</a><br>
	<a href="${pageContext.request.contextPath}/customers/stockList">View List of all stocks</a><br>
	<a href="${pageContext.request.contextPath}/customers/suggestedStock">View list of suggested stocks</a><br>
	<a href="${pageContext.request.contextPath}/customers/orderList">View your current and past orders</a><br>

	
	<a href="${pageContext.request.contextPath}">Return to main page</a>
</body>
</html>