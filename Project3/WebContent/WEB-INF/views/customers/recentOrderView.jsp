<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recent Orders</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
    <p>Recent Orders:${stockSymbol}</p>
    <c:choose>
    	<c:when test="${not empty orderList}">
    		
			<table border="2" cellpadding="5" cellspacing="1">
				<tr>
					<th>Id</th>
					<th>Stock Symbol</th>
					<th>NumShares</th>
					<th>Date Placed</th>
					<th>Date Executed</th>
					<th>Final Price Per Share</th>
					<th>PriceType</th>
					<th>OrderType</th>
					<th>View Details</th>
				</tr>
				<c:forEach items="${orderList}" var="fullOrder">
					<tr>
						<td>${fullOrder.id}</td>
						<td>${fullOrder.stockId}</td>
						<td>${fullOrder.numShares}</td>
						<%--Date Placed --%>
						<td>
							<fmt:formatDate type="both" value="${fullOrder.dateTime}"/>
						</td>
						
						<%--Final Date Time --%>
						<td>
						<c:choose>
							<c:when test="${not empty fullOrder.finalDateTime}">
									<fmt:formatDate type="both" value="${fullOrder.finalDateTime}"/> 
							</c:when>
						
							<c:otherwise>
								N/A
							</c:otherwise>
						</c:choose>
						</td>
						
						<%--Final Price Per Share --%>
						<td>
						<c:choose>
						
							<c:when test="${fullOrder.finalPricePerShare!=0}">
								${fullOrder.finalPricePerShare}
							</c:when>
							<c:otherwise>
								N/A
							</c:otherwise>
						</c:choose>
						</td>
						
						<td>${fullOrder.priceType}</td>
						<td>${fullOrder.orderType}</td>
						
						<%--View Details of the order --%>
						<td>
						<%-- If order is trailing stop --%>
						<c:choose>
						
							<c:when test="${fullOrder.priceType eq 'Trailing Stop'}">
								<a href="trailingStopHistory?id=${fullOrder.id}&tId=${fullOrder.transactionId}">Details</a>
							</c:when>
							
							<c:when test="${fullOrder.priceType eq 'Hidden Stop'}">
								<a href="hiddenStopHistory?id=${fullOrder.id}&tId=${fullOrder.transactionId}&sId=${fullOrder.stockId}">Details</a>
							</c:when>
							
							<c:otherwise>
								<a href="orderDetails?id=${fullOrder.id}">Details</a>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				
				</c:forEach>
			
			</table>
    	</c:when>
    	<c:otherwise>
    		You have no prior or current orders
    	</c:otherwise>
    </c:choose>
    
    	<a href = "${pageContext.request.contextPath}/customers">Return to main page</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>