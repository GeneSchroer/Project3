<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order List</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

	<h3>Current and Past Orders</h3>
<c:choose>
	<c:when test="${not empty fullOrderList}">
		
		<div style="overflow-x:auto;">
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Stock<br/> Symbol</th>
				<th>Shares</th>
				<th>Date Placed</th>
				<th>Date Executed</th>
				<th>Final Price<br/> (Per Share)</th>
				<th>PriceType</th>
				<th>OrderType</th>
			</tr>
			<c:forEach items="${fullOrderList}" var="fullOrder">
			<tr>
				<td style="text-align:center;">${fullOrder.id}</td>
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
						<fmt:formatNumber type="currency" value="${fullOrder.finalPricePerShare}"/>
					</c:when>
					<c:otherwise>
						N/A
					</c:otherwise>
				</c:choose>
				</td>
				<%--Price Type --%>
				<td>
				<c:choose>
					<c:when test="${fullOrder.priceType=='Trailing Stop'}">
						Trailing Stop<br>
						<a class="orderdetails" href="trailingStopHistory?id=${fullOrder.id}&tId=${fullOrder.transactionId}">Details</a>
						
					</c:when>
					<c:when test="${fullOrder.priceType=='Hidden Stop'}">
						${fullOrder.priceType}<br>
						<a class="orderdetails" href="hiddenStopHistory?id=${fullOrder.id}&tId=${fullOrder.transactionId}&sId=${fullOrder.stockId}">Details</a>
					</c:when>
					<c:otherwise>
						${fullOrder.priceType}
					</c:otherwise>
				</c:choose>
				</td>
				
				<td>${fullOrder.orderType}</td>
				
					
			</tr>
		
			</c:forEach>
	
		</table></div>
		
	</c:when>
	<c:otherwise>
		<p>No orders have been placed.</p>
	</c:otherwise>
</c:choose>
	<a class="returnbtn" href="${pageContext.request.contextPath}/customers/placeOrder">Place an order</a>
	<a class="returnbtn" href="${pageContext.request.contextPath}/customers">Return to customer menu</a>

</body>
</html>