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
	<title>View List of Orders</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	
	<p style="color: red;">${errorString}</p>
	
	<form method="POST" action="doOrderList">
	<div class="managerform">
	<ul>
	<li>
		<span style="color:crimson;">By Stock</span>
		<select name="stockSymbol">
			<option value="null">None</option>
			<c:forEach items="${stockList}" var="stock">
				<option value = "${stock.stockSymbol }">${stock.companyName } ${stock.stockSymbol }</option> 
			</c:forEach>	
		</select>
	</li>
	<li>
		<span style="color: crimson;width:100px; margin-top:5px;">
			By Customer
		</span>
	</li>
	
	<li>
		<span class="errordetails">
			${errorStrFirstName}
		</span>
		<span>
			First Name
		</span>
		<input type="text" name="firstName" value="${firstName}"/><br>
	</li>
	<li>
		<span class="errordetails">
			${errorStrLastName}
		</span>
		<span>
			Last Name
		</span>
		<input type="text" name="lastName" value="${lastName}"/><br>
	</li>
	<li>
		<input type="submit" value="Submit"/><br>
	</li>
</ul>
</div>

</form>
<c:if test="${not empty orderList}">
	<table "border="2" cellpadding="5" cellspacing="1">
	<tbody>
	<tr>
		<th>Order Id</th>
		<th>Stock</th>
		<th>Account Id</th>
		<th>Broker Id</th>
		<th>Order Type</th>
		<th># of Shares</th>
		<th>Date placed</th>
		<th >Date executed</th>
		<th>Price Per Share</th>
		<th>Price Type</th>

		</tr>
	<c:forEach items="${orderList}" var="fullOrder">
	<tr>
		<td>${fullOrder.id}</td>
		<td>${fullOrder.stockId}</td>
		<td>${fullOrder.accountId}</td>
		<td>${fullOrder.brokerId}</td>
		<td>${fullOrder.orderType}</td>
		<td>${fullOrder.numShares}</td>
		<td >
			<fmt:formatDate type="both" value="${fullOrder.dateTime}"/>
		</td>
		<td>
			<fmt:formatDate type="both" value="${fullOrder.finalDateTime}"/>
		</td>
		<td>
			<fmt:formatNumber type="currency" value="${fullOrder.finalPricePerShare}"/>
		</td>	
		<td>${fullOrder.priceType}</td>
				
	</tr>
	</c:forEach>
	</tbody>
	</table>
</c:if>
<br>
	<a class="returnbtn" href="${pageContext.request.contextPath}/managers">Return to manager page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>