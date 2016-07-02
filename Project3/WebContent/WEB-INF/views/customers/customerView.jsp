<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Transactions</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<ul class="mainlist">
		<li>
			<a class="mainlist" href="${pageContext.request.contextPath}/customers/stockHolding">View your stock portfolio</a><br/>
		</li>
		<li>
			<a class="mainlist" href="${pageContext.request.contextPath}/customers/stockHistory">Look at history of a stock</a><br/>
		</li>
		<li>
			<a class="mainlist" href="${pageContext.request.contextPath}/customers/stockList">View List of all stocks</a><br/>
		</li>
		<li>
			<a class="mainlist" href="${pageContext.request.contextPath}/customers/bestSellerList">View best seller list</a><br/>
		</li>
		<li>
			<a class="mainlist" href="${pageContext.request.contextPath}/customers/suggestedStock">View list of suggested stocks</a><br/>
		</li>
		<li>
			<a class="mainlist" href="${pageContext.request.contextPath}/customers/orderList">View your current and past orders</a><br/>
		</li>
	
	</ul>
	
	<a class="returnbtn" href="${pageContext.request.contextPath}">Return to main page</a>
</body>
</html>