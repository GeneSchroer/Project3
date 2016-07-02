<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Suggestion</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Generate List of Stock Suggestions</h3>
	<form method="POST" action="doStockSuggestionList">
		Choose a customer:
		<select name="clientId">
			<c:forEach items="${clientList}" var="client">
				<option value = "${client.id}">${client.firstName} ${client.lastName} 		             Id: ${client.id}</option>
			</c:forEach>
		</select>
		<input type="submit" value="Submit"/>
	</form>
	
	
	<c:choose>
		<c:when test="${not empty suggestedStockList}">
			<p>Stock Suggestions for ${targetClient.firstName} ${targetClient.lastName}:
			<table border="1" cellpadding="1" cellspacing="1">
			<tr>
				<th>Symbol:</th>
				<th>Name:</th>
				<th>Type:</th>
				<th>Price Per Share:</th>
			</tr>
			<c:forEach items="${suggestedStockList}" var="stock">
			<tr>
				<td>${stock.stockSymbol}</td>
				<td>${stock.companyName}</td>
				<td>${stock.type}</td>
				<td>${stock.pricePerShare}</td>
			</tr>
			</c:forEach>
			</table>
		</c:when>
		<c:when test="${not empty noStocks}">
			<p>No stocks were found</p>
		</c:when>
	</c:choose>	
	<a href="${pageContext.request.contextPath}/representatives">Return to main page</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>