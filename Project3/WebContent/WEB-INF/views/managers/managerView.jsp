<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager Transactions</title>
<h1>Manager Transactions</h1>
</head>
<body>
	
	<br>
	<a href="${pageContext.request.contextPath}/managers/employeeList">View List of Employees</a><br>
	<a href="${pageContext.request.contextPath}/managers/salesReport">Obtain sales report for a particular month</a><br>
	<a href="${pageContext.request.contextPath}/managers/stockList">View List of Stocks</a><br>
	<a href="${pageContext.request.contextPath}/managers/orderList">View list of orders by stock or customer name</a><br>
	<a href="${pageContext.request.contextPath}/managers/summaryListing">Produce Summary Listing of revenue generated by stock, stock type, or customer</a><br>
	<a href="${pageContext.request.contextPath}/managers/mostGeneratedRevenue">View customer and representative that generated most revenue</a><br>
		
	<a href="${pageContext.request.contextPath}">Return to main page</a>
</body>
</html>