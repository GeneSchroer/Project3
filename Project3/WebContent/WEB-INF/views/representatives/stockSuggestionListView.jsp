<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="doRepStockSuggestionList">
		Choose a customer:
		<select name="clientId">
			<c:forEach items="${clientList}" var="client">
				<option value = "${client.id}">${client.firstName} ${client.lastName} 		             Id: ${client.id}</option>
			
			
			</c:forEach>
		
		</select>
	
	
	<input type="submit" value="Submit"/>
	</form>
	
	<c:if test="${not empty suggestedStockList}">
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
	</c:if>

	<a href="${pageContext.request.contextPath}/representatives">Return to main page</a>

</body>
</html>