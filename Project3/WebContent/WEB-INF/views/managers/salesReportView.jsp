<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sales Report</title>
</head>
<body>
	<form method="Post" action="doSalesReport">
	Select Date:<br>
	<p style="color: red;">${errorStrYear}</p> 
	Year: <input type="text" name="year" value="${year}"/><br>
	<p style="color: red;">${errorStrMonth}</p> 
	Month: <input type="text" name="month" value="${month}"/><br>
		
	
	<c:if test="${not empty salesReportList}">
	<table border="2" cellpadding = "5" cellspacing="1">
		<tr>
			<th>Date</th>
			<th>Stock Symbol</th>
			<th>Company Name</th>
			<th># Shares</th>
			<th>Price Per Share</th>
			<th>Order Type</th>
			<th>Price Type</th>
		</tr>
		
		<c:forEach items="${salesReportList}" var="salesReport">
		<tr>
			<td>${salesReport.dateTime}</td>
			<td>${salesReport.stockSymbol}</td>
			<td>${salesReport.companyName}</td>
			<td>${salesReport.numShares}</td>
			<td>${salesReport.pricePerShare}</td>
			<td>${salesReport.orderType}</td>
			<td>${salesReport.priceType}</td>
		</tr>
		
		</c:forEach>
	
	
	
	</table>
	
	
	</c:if>
	
	
	<input type="submit" value="Submit"/>
	
	</form>
	<a href="${pageContext.request.contextPath}/managers">Return to manager page</a>
</body>
</html>