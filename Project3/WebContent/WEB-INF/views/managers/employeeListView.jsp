<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee List</title>
</head>
<body>

<table border="2" cellpadding="5" cellspacing="1">
	<tr>
		<th>Id</th>
		<th>Last Name</th>
		<th>First Name</th>
		<th>SSN</th>
		<th>Start Date</th>
		<th>Hourly Rate</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<c:forEach items="${employeeList}" var="employee">
		<tr>
			<td>${employee.id}</td>
			<td>${employee.lastName}</td>
			<td>${employee.firstName}</td>
			<td>${employee.SSN}</td>
			<td>${employee.startDate}</td>
			<td>${employee.hourlyRate }</td>
			<td><a href="editEmployee?id=${employee.id}">Edit</a></td>
			<td><a href="deleteEmployee?id=${employee.id}">Delete</a></td>
		</tr>
	
	</c:forEach>

</table>
<a href="${pageContext.request.contextPath}/managers/employeeList/createEmployee">Add Employee</a>
<a href="${pageContext.request.contextPath}/managers">Return to manager view</a>

</body>
</html>