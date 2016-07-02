<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trailing Stop History</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h2>Trailing Stop History</h2>
	<c:choose>
		
		<c:when test="${not empty trailingHistoryList }">
			
			<div style="margin: 10px; padding: 3px; display:inline-block; background-color: lightblue;font-size:1.05em;">Percentage: 
			<fmt:formatNumber value="${order.percentage/100}" type="PERCENT"/> 
			trailing stop</div>
			
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
					<p style="color: red;">					<fmt:formatDate type="both" value="${trailingHistory.dateTime}"/> 
</p>
				</c:when>
				
				<c:otherwise>		
					<fmt:formatDate type="both" value="${trailingHistory.dateTime}"/> 
				</c:otherwise>
				
				</c:choose>
				</td>
				
				<td align="center">
				<c:choose>
				
				<c:when test="${not empty transaction.dateTime and trailingHistory.dateTime eq transaction.dateTime}">
					<p style="color: red; text-decoration:underline; font-size:1.2em;">
					<fmt:formatNumber type="currency" value="${trailingHistory.pricePerShare}"/>
					</p>
				</c:when>
				
				<c:otherwise>
					<fmt:formatNumber type="currency" value="${trailingHistory.pricePerShare}"/>
				</c:otherwise>
				
				</c:choose>
				</td>
			</tr>
			</c:forEach>
			</table>
		</c:when>
	</c:choose>
	<a class="returnbtn" href="${pageContext.request.contextPath}/customers/orderList">Return to Order List</a><br>
	<jsp:include page="_footer.jsp"></jsp:include>
	
</body>
</html>