<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Record Order</title>
</head>
<body>
<form method="POST" action="doRecordOrder">
	Select a Stock:
	<select name="stockSymbol">
		<c:forEach items="${stockList}" var="stock">
			<option value = "${stock.stockSymbol }">${stock.companyName } ${stock.stockSymbol }</option> 
		
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
	<option value="Market On Close">Market on Close</option>
	<option value="Trailing Stop">Trailing Stop</option>
	<option value="Hidden Stop">Hidden Stop</option>
	</select><br>
	
	Date and Time<br>
	(YYYY-MM-DD HH:MI:SS):<br>
	<input type="text" name="dateTime"> 
	<input type ="checkbox" name="now" value="now"/> now<br>
	
	NumShares:
	<input type ="text" name="numShares" min="1"/><br>
	
	Percentage:
	<input type="number" name="percentage" min="0"/><br>
	<input type=submit value="Submit"/>
	
	</form>
<a href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
</body>
</html>