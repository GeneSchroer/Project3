<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
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
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>