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
    <h1>Representative Transactions</h1>
	<ul>
	<li>
		<a href="${pageContext.request.contextPath}/representatives/recordOrderB">Record an order for a Client</a><br>
	</li>
	<li>
	<a href="${pageContext.request.contextPath}/representatives/clientList">View List of Clients</a><br>
	</li>
	<li>
	<a href="${pageContext.request.contextPath}/representatives/employeeList">View List of Employees</a><br>
	</li>
	<li>
	<a href="${pageContext.request.contextPath}/representatives/mailingList">Produce customer mailing list</a><br>	
	</li>
	<li>
	<a href="${pageContext.request.contextPath}/representatives/stockSuggestionList">Generate Stock Suggestion List</a><br>	
	</li>
	<li>
	<a href="${pageContext.request.contextPath}">Return to main page</a>
	</li>
	</ul>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>