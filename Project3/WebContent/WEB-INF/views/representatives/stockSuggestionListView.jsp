<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeForms.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Suggestion</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Generate List of Stock Suggestions</h3>
	<c:choose>
		<c:when test="${not empty clientList}">
			<form method="POST" action="doStockSuggestionList">
				<div class="managerform">
				<ul>
					<li>
						<span style="width:110px;">
							Customer
							<span class="managertooltip">
						Generate a list of suggested stocks for a specific customer
					</span>	
						</span>
						<select style="width:200px;" name="clientId">
							<c:forEach items="${clientList}" var="client">
								<option value = "${client.id}">${client.firstName} ${client.lastName} 		             Id: ${client.id}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<input type="submit" value="Submit"/>
					</li>
				</ul>
				</div>
			</form>
		
	
			<c:choose>
				<c:when test="${not empty suggestedStockList}">
					<p style=
					"background-color:silver; 
					border-radius: 2px;
					font-size:1.02em;
					
					display:inline-block;
					padding:10px;">
						Stock Suggestions for ${targetClient.firstName} ${targetClient.lastName}
					</p>
					<table border="1" cellpadding="1" cellspacing="1">
					<tr>
						<th>Symbol:</th>
						<th>Name:</th>
						<th>Type:</th>
						<th>Price Per Share:</th>
					</tr>
					<c:forEach items="${suggestedStockList}" var="stock">
					<tr>
						<td>${stock.stockSymbol}</td>
						<td>${stock.companyName}</td>
						<td>${stock.type}</td>
						<td>${stock.pricePerShare}</td>
					</tr>
					</c:forEach>
					</table>
				</c:when>
				<c:when test="${not empty noStocks}">
					<p class="nonform">No stocks were found</p>
				</c:when>
			</c:choose>	
		</c:when>
		<c:otherwise>
			<p class="nonform">You do not have any clients</p>
		</c:otherwise>
	</c:choose>
	<br/>
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives">Return to main page</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>