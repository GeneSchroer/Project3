<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock List</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
    <h3>List of all stocks:</h3>
	<c:choose>
	<c:when test="${not empty stockList}">
		<c:choose>
			<c:when test="${not empty bestSellerList}">
				<div style="float:right;"  class="portfoliolist">
					<button class="hasStock">Hover here for best-selling stocks</button>
					<div class="portfoliolist-content">
						<div class="bestseller">
						<table>
							<tr>
								<th>Stock</th>
								<th>Sold</th>
						
							</tr>
							<c:forEach items="${bestSellerList}" var="bestSeller">
							<tr>
								<td>${bestSeller.stockId}</td>
								<td>${bestSeller.totalShares}</td>
							</tr>					
							</c:forEach>
						</table>		
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<button class="hasStock">No stocks have been sold</button>
	</c:otherwise>
	</c:choose>	
		
		<table border="2" cellpadding="5" cellspacing="1">
			<tr>
				<th>Stock Symbol</th>
				<th>Company Name</th>
				<th>Type</th>
				<th>Price Per Share</th>
				<th>Stocks Available</th>
				<%--<th>Place Order</th>--%>
			</tr>
			<c:forEach items="${stockList}" var="stock">
				<tr>
					<td>${stock.stockSymbol}</td>
					<td>${stock.companyName}</td>
					<td>${stock.type}</td>
					<td>
						<fmt:formatNumber type="currency" value="${stock.pricePerShare}"/>
					</td>
					<td>
					<c:choose>
						<c:when test = "${stock.numShares!=0}">
							${stock.numShares}
						</c:when>
						<c:otherwise>
						None
						</c:otherwise>
					</c:choose>
					</td>
					<%--<td><a href="${pageContext.request.contextPath}/customers/placeOrder?stockSymbol=${stock.stockSymbol}">Edit</a>--%>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<p>No stocks are available.</p>
	</c:otherwise>
	</c:choose>
	<a class="returnbtn" href="${pageContext.request.contextPath}/customers/placeOrder">Place Order</a>
	<a class="returnbtn" href="${pageContext.request.contextPath}/customers">Return to main page</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>