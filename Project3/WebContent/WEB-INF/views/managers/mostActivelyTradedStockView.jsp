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
	<c:choose>
	<c:when test=${not empty mostActivelyTradedStocksList }>
		<table border="2">
			<tr>
			<th>Stock Name</th>
			<th>Company Name</th>
			<th>Type</th>
			<th>Current Price Per Share</th>
			</tr>
			
			<c:forEach items="${mostActivelyTradeStocksList }" var="stock">
				<tr>
					<td>${stock.stockSymbol}</td>
					<td>${stock.companyName}</td>
					<td>${stock.type}</td>
					<td>${stock.pricePerShare}</td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	
	<c:otherwise>
	No stocks have been traded
	</c:otherwise>
	</c:choose>
	<a href="${pageContext.request.contextPath}/managers">Return to Manager page</a>

</body>
</html>