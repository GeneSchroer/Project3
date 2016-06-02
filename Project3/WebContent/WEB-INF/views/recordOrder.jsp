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
	<select name="stockBox">
		<c:forEach items="${stockList}" var="stock">
			<option value = "${stock.stockSymbol }">${stock.companyName } ${stock.stockSymbol }</option> 
		
		</c:forEach>
	</select><br>
	Order Type:
	<select name="OrderBox">
		<option name="Buy">Buy</option>
		<option>Sell</option>
	</select><br>
	Price Type:
	<select name="PriceType">
	<option>Market</option>
	<option>Market on Close</option>
	<option>Trailing Stop</option>
	<option>Hidden Stop</option>
	</select><br>
	
	NumShares:
	<input type ="number" name="#shares" min="1"/><br>
	
	Percentage:
	<input type="number" name="percent" min="0"/><br>
	<input type=submit value="Submit"/>
	
	</form>
<a href="${pageContext.request.contextPath}/representative">Return to representative view</a>
</body>
</html>