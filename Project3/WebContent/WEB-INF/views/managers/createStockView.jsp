<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Stock</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Create New Stock</h3>

	<form method="POST" action="doCreateStock">

	<!-- Stock Symbol -->
	<p style="color: red;">${errorStrStockSymbol}</p>
    Stock Symbol:<br>
    <input type="text" name="stockSymbol" value="${stockSymbol}">
	
	<!-- Company Name -->
	<p style="color: red;">${errorStrCompanyName}</p>
    Company Name:<br>
    <input type="text" name="companyName" value="${companyName}">
	
	<!-- Type -->
  	<p style="color: red;">${errorStrType}</p>
  	Stock Type:<br>
  	<input type="text" name="type" value="${type}">
	
	<!-- Price Per Share -->
	<p style="color: red;">${errorStrPricePerShare}</p>
  	Price Per Share:<br>
  	<input type="text" name="pricePerShare" value="${pricePerShare}"><br>
	
	<!-- Price Per Share -->
	<p style="color: red;">${errorStrNumShares}</p>
  	Shares Available:<br>
  	<input type="text" name="numShares" value="${numShares}"><br>
	
	
	<input type="submit" value="Submit"/>
	<a href = "${pageContext.request.contextPath}/managers/stockList">Return to Stock List</a>
	</form>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>