<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
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
			<div class="managerform">
			
			<p style="color: red;">${errorStrPPS}</p><br>
			<input type="hidden" name="stockSymbol" value="${stock.stockSymbol}"/>
			<input type="hidden" name="companyName" value="${stock.companyName}"/>
			<input type="hidden" name="type" value="${stock.type}"/>
			<ul>
			<li>
				<span style="background-color:silver; width: 300px;">
					${stock.stockSymbol } ${stock.companyName}
				</span>
			</li>
			
			<%--Price Per Share --%>
			<li>
				<span class="errordetails">
					${errorStrPricePerShare}
				</span>
				<span>
					Share Price
				</span>
				<input type="text" name="pricePerShare"  value="${stock.pricePerShare}" required/>
			</li>	
			
			<%--Num Shares --%>
			<li>
				<span class="errordetails">
					${errorStrNumShares}
				</span>
				<span style="width:125px;">
					Shares Available
				</span>
				<input type="text" name="numShares"  value="${stock.numShares}" required/>
			</li>
			<li>
				<input type="submit" value="Submit"/>
			</li>
			</ul>
			</div>
		</form>
	</c:when>
	<c:otherwise>
		<div class="returnbtn"><span>No Stock Selected.</span> </div>
	</c:otherwise>
	</c:choose>
	<a class="returnbtn" href = "${pageContext.request.contextPath}/managers">Return to Manager page</a>
	<a class="returnbtn" href="${pageContext.request.contextPath}/managers/stockList">Return to Stock List</a>
	
	<jsp:include page="_footer.jsp"></jsp:include>
	
</body>
</html>