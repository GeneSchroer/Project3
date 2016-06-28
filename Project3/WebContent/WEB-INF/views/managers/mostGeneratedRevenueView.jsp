<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Persons with most generated revenue</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
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
		<td>$${bestRepresentative.revenue}</td>
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
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>