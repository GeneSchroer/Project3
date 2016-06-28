<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Update Stock</h3>
	<c:choose>
	
	<c:when test="${not empty stock}">
		<form method="POST" action="${pageContext.request.contextPath}/managers/doUpdateStock">
			<p style="color: red;">${errorStrPPS}</p><br>
			<input type="hidden" name="stockSymbol" value="${stock.stockSymbol}"/>
			<input type="hidden" name="companyName" value="${stock.companyName}"/>
			<input type="hidden" name="type" value="${stock.type}"/>
			
			<p>${stock.stockSymbol}</p>
			<p>${stock.companyName }</p>
			
			<%--Price Per Share --%>
			<p style="color: red;">${errorStrPricePerShare}</p>
			<p>Price Per Share:</p>
			<input type="text" name="pricePerShare"  value="${stock.pricePerShare}"><br>
			
			<%--Num Shares --%>
			<p style="color: red;">${errorStrNumShares}</p>
			<p>Shares Available:</p>
			<input type="text" name="numShares"  value="${stock.numShares}"><br>
			
			<input type="submit" value="Submit"/>
			<a href="${pageContext.request.contextPath}/managers/stockList">Return to Stock List</a>
			
		</form>
	</c:when>
	<c:otherwise>
		No Stock Selected. 
		<a href = "${pageContext.request.contextPath}/managers/stockList">Return to Stock List?</a><br>
	</c:otherwise>
	</c:choose>
	<a href = "${pageContext.request.contextPath}/managers">Return to Manager page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
	
</body>
</html>