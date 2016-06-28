<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Stock</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	 <h3>Edit Stock Name</h3>
	 <c:choose>
	 	<c:when test="${not empty stockSymbol}">
	 		<form method="POST" action="doEditStock">
	 		<p style="color:red;">Stock Symbol: ${stockSymbol}</p>
			<p style="color:red;">Price Per Share: ${pricePerShare}</p>
			<input type="hidden" name="stockSymbol" value="${stockSymbol}"/>
			<input type="hidden" name="pricePerShare" value="${pricePerShare}"/>
	
	
			<%-- Company Name --%>
			<p style="color: red;">${errorStrCompanyName}</p> 
			Company Name:<br>
			<input type="text" name="companyName" value="${companyName}"><br>
	
			<%-- Type --%>
			<p style="color: red;">${errorStrType}</p> 
			Stock Type:<br>
			<input type="text" name="type" value="${type}"><br>
	  
	  
			<input type="submit" value="Submit"/>
			</form>
	</c:when>
	<c:otherwise>
		<p>No stock chosen.</p>
	</c:otherwise>
	</c:choose>
	<a href="${pageContext.request.contextPath}/managers/stockList">Return to stock list</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>