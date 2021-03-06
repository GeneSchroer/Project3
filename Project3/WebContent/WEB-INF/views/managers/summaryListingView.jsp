<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
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
		document.getElementById("firstName").required=false;
		document.getElementById("lastName").required=false;
	}
	function enableStockTypeSearch(){
		document.getElementById("stockType").disabled=false;
		document.getElementById("stockSymbol").disabled=true;
		document.getElementById("firstName").disabled=true;
		document.getElementById("lastName").disabled=true;
		document.getElementById("stockSymbolCheckBox").checked=false;
		document.getElementById("customerNameCheckBox").checked=false;
		document.getElementById("firstName").required=false;
		document.getElementById("lastName").required=false;
	}
	function enableCustomerNameSearch(){
		document.getElementById("firstName").disabled=false;
		document.getElementById("lastName").disabled=false;
		document.getElementById("stockSymbol").disabled=true;
		document.getElementById("stockType").disabled=true;
		document.getElementById("stockSymbolCheckBox").checked=false;
		document.getElementById("stockTypeCheckBox").checked=false;
		
		document.getElementById("firstName").required=true;
		document.getElementById("lastName").required=true;
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
	
	<div style="float:right; margin-right:200px; z-index:2;"  class="dropdown">
			<button class="hasStock">Hover here for best-selling stocks</button>
			<div class="dropdown-content">
				Customer Representative with most generated revenue:
				<table border="2" cellpadding="5">
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Id</th>
						<th>Revenue</th>
					</tr>
					<tr>
						<td>${bestRepresentative.firstName}</td>
						<td>${bestRepresentative.lastName}</td>
						<td>${bestRepresentative.id}</td>
						<td>
							<fmt:formatNumber type="currency" value="${bestRepresentative.revenue * 0.05}"/>
						</td>
					</tr>
				</table>
				Customer with most generated revenue:
				<table border="2" cellpadding="5">
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Id</th>
						<th>Revenue</th>
					</tr>
					<tr>
						<td>${bestCustomer.firstName}</td>
						<td>${bestCustomer.lastName}</td>
						<td>${bestCustomer.id}</td>
						<td>$${bestCustomer.revenue}</td>
					</tr>
					</table>
				</div>
			</div>
		
		</div>
	
	
<p style="color:red;">${errorString}</p>
<form method="POST"  action="doSummaryListing">
	<div class="managerform">
	<ul>
		<li>
		
			<span style="margin-bottom:5px;">
				Stock
				<span  class="managertooltip">
					View a summary listing of a stock
				</span>
			</span>
			<input style="margin-left:0px;" type="checkbox" id="stockSymbolCheckBox" name="stockSymbolCheckBox" onChange="enableStockSymbolSearch()"/>
			<select style="margin-left:20px;" id="stockSymbol" name="stockSymbol" disabled>
				<option value = "null">None</option>
				<c:forEach items="${stockSymbolList}" var="stock">
					<option value="${stock.stockSymbol}">${stock.stockSymbol} ${stock.companyName}</option>
				</c:forEach>
			</select>
		</li>
		
		<li>
			<span style="margin-bottom:5px;">
				Type
				<span class="managertooltip">
					Type of stock (e.g. Automobile, Computer, etc.)
				</span>
			</span>
			<input style="margin-left:0px;" type="checkbox" id="stockTypeCheckBox" name="stockTypeCheckBox" onChange="enableStockTypeSearch()"/>
			
			<select style="margin-left:20px;" id="stockType" name="stockType" disabled>
				<option value = "null">None</option>
				
				<c:forEach items ="${stockTypeList}" var="string">
					<option value="${string}">${string}</option>
				</c:forEach>
			</select>
		</li>
		
		<li>
			<span>
				Name
				<span class="managertooltip">
					View the total revenue generated by a customer
				</span>
			</span>
			<input style="margin-left:0px;" type="checkbox" id="customerNameCheckBox" name="customerNameCheckBox" onChange="enableCustomerNameSearch()"/>
		</li>
		<li>
			
			<span class="errordetails">
				${errorStrFirstName}
			</span>
			<span>
				First Name
				
			</span>
			<input type="text" id="firstName" name="firstName" value="${firstName}" disabled/><br>
		</li>
		
		<li>
			<span class="errordetails">
				${errorStrLastName}
			</span>
			<span>
				Last Name
			</span>
			<input type="text" id="lastName" name="lastName" value="${lastName}" disabled/><br>
		</li>
		<li>
			<input type="submit" value="Submit"/><br>
		</li>
	</ul>
	</div>
	</form>
	<c:choose>
		<c:when test="${not empty stockSymbolListing and hasListing==true}">
			<table>
			<tr>
				<th style="padding:5px;">
					Symbol
				</th>
				<th style="padding:5px;">
					Name
				</th>
				<th style="padding:5px;">
					Revenue
				</th>
			</tr>
			<tr>
				<td style="padding:5px;">
					${stockSymbolListing.stockSymbol}
				</td>
				<td style="padding:5px;">
					${stockSymbolListing.companyName}
				</td>
				<td style="padding:5px;">
					<fmt:formatNumber type="currency" value="${stockSymbolListing.revenue}"/>
				</td>
			</tr>
			</table>
		</c:when>
		<c:when test="${not empty stockTypeListing and hasListing==true}">
			<table>
			<tr>
				<th style="padding:5px;">
					Type
				</th>
				<th style="padding:5px;">
					Revenue
				</th>
			</tr>
			<tr>
				<td style="padding:5px;">
					${stockTypeListing.type}
				</td>
				<td style="padding:5px;">
					<fmt:formatNumber type="currency" value="${stockTypeListing.revenue}"/>		
				</td>
			</tr>
		</table>
		</c:when>
		<c:when test="${not empty customerNameListing and hasListing==true}">
			<div style="padding:10px;">
			<table>
			<tr >
				<th style="padding:5px;">
					Name
				</th>
				<th style="padding:5px;">
					Revenue
				</th>
			</tr>
			<tr>
				<td style="padding:10px;">
					${customerNameListing.firstName} 
					${customerNameListing.lastName}
				</td>
				<td style="padding: 10px;">
					<fmt:formatNumber type="currency" value="${customerNameListing.revenue}"/>
				</td>
			</tr>	
			</table>
			</div>
		</c:when>
		<c:when test="${hasListing==true}">
			No listing could be found.
		</c:when>
	</c:choose>
<br>
<a class="returnbtn" href="${pageContext.request.contextPath}/managers">Return to manager page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>