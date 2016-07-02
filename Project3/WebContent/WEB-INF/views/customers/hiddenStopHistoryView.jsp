<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hidden Stop History</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

<h3>Hidden Stop History</h3>

	<span style="background-color: darkblue; border-radius:5px; color:white; display:inline; margin:10px; padding:3px;">
		Stop price: 
		<fmt:formatNumber type="currency" value="${order.pricePerShare}"/>
	</span><br/>
	<c:choose>
		<c:when test="${not empty hiddenHistoryList }">
				<table border="2">
				<tr>
					<th>Date Time:</th>
					<th>Price Per Share</th>
				</tr>
				<c:forEach items="${hiddenHistoryList}" var="hiddenHistory">
				<tr>
					<td>	
					<c:choose>
						<c:when test="${not empty transaction.dateTime and hiddenHistory.dateTime eq transaction.dateTime}">
							<span style="color: red;">
								<fmt:formatDate type="both" value="${hiddenHistory.dateTime}"/>
							</span>
						</c:when>
						<c:otherwise>		
								<fmt:formatDate type="both" value="${hiddenHistory.dateTime}"/>
						</c:otherwise>
					</c:choose>
					</td>
					<td align="center">
					<c:choose>
						<c:when test="${not empty transaction.dateTime and hiddenHistory.dateTime eq transaction.dateTime}">
							<span style="color: red;">
								<fmt:formatNumber type="currency" value="${hiddenHistory.pricePerShare}"/>
							</span>
						</c:when>
						<c:otherwise>
							<fmt:formatNumber type="currency" value="${hiddenHistory.pricePerShare}"/>
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