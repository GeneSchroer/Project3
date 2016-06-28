<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Revenue Summary</title>
<script language="javascript" type="text/javaScript">
	function enableStockSymbolSearch(){
		document.getElementById("stockSymbol").disabled=false;
		document.getElementById("stockType").disabled=true;
		document.getElementById("firstName").disabled=true;
		document.getElementById("lastName").disabled=true;
		document.getElementById("stockTypeCheckBox").checked=false;
		document.getElementById("customerNameCheckBox").checked=false;
	}
	function enableStockTypeSearch(){
		document.getElementById("stockType").disabled=false;
		document.getElementById("stockSymbol").disabled=true;
		document.getElementById("firstName").disabled=true;
		document.getElementById("lastName").disabled=true;
		document.getElementById("stockSymbolCheckBox").checked=false;
		document.getElementById("customerNameCheckBox").checked=false;
	}
	function enableCustomerNameSearch(){
		document.getElementById("firstName").disabled=false;
		document.getElementById("lastName").disabled=false;
		document.getElementById("stockSymbol").disabled=true;
		document.getElementById("stockType").disabled=true;
		document.getElementById("stockSymbolCheckBox").checked=false;
		document.getElementById("stockTypeCheckBox").checked=false;
	}
	
	function validate(){
		return false;
	}
</script>
</head>
<body>
	

	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Revenue Summary</h3>
<p style="color:red;">${errorString}</p>
<form method="POST"  action="doSummaryListing">
	
	<input type="checkbox" id="stockSymbolCheckBox" name="stockSymbolCheckBox" onChange="enableStockSymbolSearch()"/>By Stock:
	<select id="stockSymbol" name="stockSymbol" disabled>
		<option value = "null">None</option>
		<c:forEach items="${stockSymbolList}" var="stock">
			<option value="${stock.stockSymbol}">${stock.stockSymbol} ${stock.companyName}</option>
		</c:forEach>
	</select>
	<br>
	<p><input type="checkbox" id="stockTypeCheckBox" name="stockTypeCheckBox" onChange="enableStockTypeSearch()"/>
	By Stock Type:</p>
	<select id="stockType" name="stockType" disabled>
		<option value = "null">None</option>
		<c:forEach items ="${stockTypeList}" var="string">
		<option value="${string}">${string}</option>
		
		</c:forEach>
	
	</select>
	<br>
	<p><input type="checkbox" id="customerNameCheckBox" name="customerNameCheckBox" onChange="enableCustomerNameSearch()"/>
	By Name:</p>
	
	First Name:
	<p style="color: red;">${errorStrFirstName}</p>
	<input type="text" id="firstName" name="firstName" value="${firstName}" disabled/><br>
	Last Name:
	<p style="color: red;">${errorStrLastName}</p>
	<input type="text" id="lastName" name="lastName" value="${lastName}" disabled/><br>
	
	<input type="submit" value="Submit"/><br>
	
	</form>
	<c:choose>
		<c:when test="${not empty stockSymbolListing and hasListing==true}">
			${stockSymbolListing.stockSymbol}
			${stockSymbolListing.companyName}
			${stockSymbolListing.revenue}
		</c:when>
		<c:when test="${not empty stockTypeListing and hasListing==true}">
			${stockTypeListing.type}
			${stockTypeListing.revenue}		
		</c:when>
		<c:when test="${not empty customerNameListing and hasListing==true}">
			${customerNameListing.firstName}
			${customerNameListing.lastName}
			${customerNameListing.revenue}
		</c:when>
		<c:when test="${hasListing==true}">
			No listing could be found.
		</c:when>
	</c:choose>
<br>
<a href="${pageContext.request.contextPath}/managers">Return to manager page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>