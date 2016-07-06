<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
   
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
	<meta charset="UTF-8">
	<title>Sales Report</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<div class="managerform">
	<h3>Sales Report: Select a date to view</h3>
	
	<form method="Post" action="doSalesReport">
	<ul>
	<li>
		<span style="width:100px; background-color: silver;">Select Date</span>
	</li>
	<li>
		<span class="errordetails" >
			${errorStrYear}
		</span> 
		<span style="width:60px;">
			Year 
			<span class="managertooltip">
				Select Year
			</span>
		</span>
		<input type="number" name="year" value="${year}" required placeholder="YYYY"/>
	</li>
	<li>
		<span class="errordetails" >
			<b>${errorStrMonth}</b>
		</span> 
		<span style="width:60px;">
			Month
			<span class="managertooltip">
				Select Month
			</span>
		</span>	
		<input type="number" name="month" value="${month}" required placeholder="MM"/>
	</li>
	<li>
		<input type="submit" value="Submit"/>
	</li>
	</ul>
	</form>
	</div>
	<c:choose>
	<c:when test="${not empty salesReportList and not empty doSales}">
	<table border="2" cellpadding = "5" cellspacing="1">
		<tr>
			<th>Date</th>
			<th>Stock Symbol</th>
			<th>Company Name</th>
			<th># Shares</th>
			<th>Price Per Share</th>
			<th>Order Type</th>
			<th>Price Type</th>
		</tr>
		
		<c:forEach items="${salesReportList}" var="salesReport">
		<tr>
			<td><fmt:formatDate type="both" value="${salesReport.dateTime}"/></td>
			<td>${salesReport.stockSymbol}</td>
			<td>${salesReport.companyName}</td>
			<td>${salesReport.numShares}</td>
			<td><fmt:formatNumber type="currency" value="${salesReport.pricePerShare}"/></td>
			<td>${salesReport.orderType}</td>
			<td>${salesReport.priceType}</td>
		</tr>
		
		</c:forEach>
	
	
	
	</table>
	
	
	</c:when>
	<c:when test="${empty salesReportList and not empty doSales }">
		<p class="nonform">No Sales Reports found</p> <br/>
	</c:when>
	<c:otherwise>
	</c:otherwise>
	</c:choose>
	
	<a class="returnbtn" href="${pageContext.request.contextPath}/managers">Return to manager page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>