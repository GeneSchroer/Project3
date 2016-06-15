<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Place an Order</title>
</head>
<body>
	<h3>Place an order</h3>

	
<form method="POST" action="doPlaceOrder">
	Select an account to use
	<select name="accountId">
		<c:forEach items="${accountList}" var="account">
			<option value="${account.id}">${account.id}</option>
		</c:forEach>
	
	</select><br>
	
	Select a Stock:
	<select name="stockSymbol">
		<c:forEach items="${stockList}" var="stock">
			<option value="${stock.stockSymbol }">${stock.companyName } ${stock.stockSymbol }</option> 
		
		</c:forEach>
	</select><br>
	Order Type:
	<select name="orderType">
		<option value="Buy">Buy</option>
		<option value="Sell">Sell</option>
	</select><br>
	Price Type:
	<select name="priceType">
	<option value="Market">Market</option>
	<option value="Market On Close">Market On Close</option>
	<option value="Trailing Stop">Trailing Stop</option>
	<option value="Hidden Stop">Hidden Stop</option>
	</select><br>
	
	<!-- NumShares -->	
	<p style="color: red;">${errorStrNumShares}</p>
	Number of Shares:
	<input type ="text" name="numShares" value="${numShares}"/><br>
	
	<!-- Price Per Share -->
	<p style="color: red;">${errorStrPricePerShare}</p>
	Price Per Share:
	<input type="text" name="pricePerShare" value="${pricePerShare}"/><br>
	
	<!-- Percentage -->
	<p style="color: red;">${errorStrPercentage}</p>
	Percentage:
	<input type="number" name="percentage" value="${percentage}"/><br>
	
	<input type=submit value="Submit"/>
	<a href="${pageContext.request.contextPath}/customers/orderList">Return to Order List</a>
	</form>

</body>
</html>