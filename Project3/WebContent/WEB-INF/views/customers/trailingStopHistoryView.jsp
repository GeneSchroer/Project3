<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trailing Stop History</title>
</head>
<body>
<h3>Trailing Stop History</h3>
	
	<c:choose>
		<c:when test="${not empty trailingHistoryList }">
			<table border="2">
			<tr>
				<th>Date Time:</th>
				<th>Price Per Share</th>
			</tr>
			<c:forEach items="${trailingHistoryList}" var="trailingHistory">
			<tr>
				<td>	
				<c:choose>
				
				<c:when test="${not empty transaction.dateTime and trailingHistory.dateTime eq transaction.dateTime}">
					<p style="color: red;">${trailingHistory.dateTime}</p>
				</c:when>
				
				<c:otherwise>		
					${trailingHistory.dateTime}
				</c:otherwise>
				
				</c:choose>
				</td>
				
				<td align="center">
				<c:choose>
				
				<c:when test="${not empty transaction.dateTime and trailingHistory.dateTime eq transaction.dateTime}">
					<p style="color: red;">${trailingHistory.pricePerShare}</p>
				</c:when>
				
				<c:otherwise>
					${trailingHistory.pricePerShare}
				</c:otherwise>
				
				</c:choose>
				</td>
			</tr>
			</c:forEach>
			</table>
		</c:when>
	</c:choose>
	<a href="${pageContext.request.contextPath}/customers/orderList">Return to Order List</a><br>

</body>
</html>