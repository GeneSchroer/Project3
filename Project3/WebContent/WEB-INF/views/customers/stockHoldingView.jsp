<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Porfolio</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

	<c:choose>
		<c:when test="${not empty stockHoldingList}">
		<h3>Stock Portfolio</h3>
			<table border="2" cellpadding="2">
			<tr>
				<th>Account:</th>
				<th>Stock Symbol:</th>
				<th>Total Shares:</th>
				<th>Price Per Share:</th>
				<th>Total</th>
			</tr>
			<c:forEach items="${stockHoldingList}" var="stockHold">
			
			<tr>
				
				<td>${stockHold.accountId}</td>
				<td>${stockHold.stockSymbol}</td>
				<td>${stockHold.totalShares}</td>
				<td>
				<div class="dropdown"><span>Details</span>	<div class="dropdown-content">
					${stockHold.pricePerShare}
					${stockHold.pricePerShare * stockHold.totalShares }
					</div>
				</div>
				</td>
			</tr>
				
			
			</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			You have no stocks in any account
		</c:otherwise>
	</c:choose>
	<a href="${pageContext.request.contextPath}/customers">Return to customer page</a><br>
    <jsp:include page="_footer.jsp"></jsp:include>
	
</body>
</html>