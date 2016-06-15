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
		<c:when test="${not empty haveStocks}">
			<form method="POST" action="doStockHistory">
			Select Stock:			
			<select name="stockSymbol">
				<c:forEach items="${stockList}" var="stock">
					<option value="${stock.stockSymbol}">${stock.stockSymbol} ${stock.companyName}</option>
			
				</c:forEach>
			</select><br>
			Date:<br>
			From:
			<p style="color: red;">${errorStrFromDate}</p>
			<input type="text" name="fromDate" value="${fromDate}"/><br>
			To:			
			<p style="color: red;">${errorStrToDate}</p>
			<input type="text" name="toDate" value="${toDate}"/>
			<input type="submit" value="Submit"/>
			</form>
			<c:if test="${not empty stockHistoryList}">
				<table border="2">
				<tr>
					<th>Date Time:</th>
					<th>Price Per Share:</th>
				</tr>
				<c:forEach items="${stockHistoryList}" var="stockHistory">
				<tr>				
					<td>${stockHistory.dateTime}</td>
					<td>${stockHistory.pricePerShare} </td>
				</tr>
				</c:forEach>
				</table>
			
			</c:if>
			
		</c:when>
		
		<c:otherwise> There's no stocks available</c:otherwise>
		
	</c:choose>
		<a href="${pageContext.request.contextPath}/customers">Return to customer menu</a>
	
</body>
</html>