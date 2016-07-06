<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeMainPage.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Representative Transactions</title>

</head>
<body>
	
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
    <div class="mainmenu">
    <h3>Representative Transactions</h3>
	<ul>
	<li>
		<a href="${pageContext.request.contextPath}/representatives/recordOrderB">Record Order</a>
		<div class="menutooltip">
			Record an order for a Client
		</div>	
	</li>
	<li>
		<a href="${pageContext.request.contextPath}/representatives/clientList">Client List</a>
		<div class="menutooltip">
			View List of Clients
		</div>
	</li>
	<li>
		<a href="${pageContext.request.contextPath}/representatives/employeeList">Employee List</a>
		<div class="menutooltip">
			View List of Employees
		</div>
	</li>
	<li>
		<a href="${pageContext.request.contextPath}/representatives/mailingList">Mailing List</a>	
		<div class="menutooltip">
			Produce customer mailing list
		</div>
	</li>
	<li>
		<a href="${pageContext.request.contextPath}/representatives/stockSuggestionList">Stock Suggestions</a>	
		<div class="menutooltip">
			Generate Stock Suggestion List
		</div>
	</li>
	<li>
	<a href="${pageContext.request.contextPath}">Return to main page</a>
	</li>
	</ul>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>