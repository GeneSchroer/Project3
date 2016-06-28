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
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

<c:choose>
	<c:when test="${not empty employeeList}">
	<table border="2" cellpadding="5" cellspacing="1">
		<tr>
			<th>Id</th>
			<th>Last Name</th>
			<th>First Name</th>
			<th>Address</th>
			<th>Start Date</th>
			<th>Telephone</th>
			<th>Details</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<c:forEach items="${employeeList}" var="employee">
			<tr>
				<td>${employee.id}</td>
				<td>${employee.lastName}</td>
				<td>${employee.firstName}</td>
				<td>${employee.address}</td>
				<td>${employee.startDate}</td>
				<td>${employee.telephone }</td>
				<td><a href="employeeDetails?id=${employee.id}">Details</a></td>
				<c:choose>
				<c:when test="${employee.id!=loginedUser.id }">
					<td><a href="editEmployee?id=${employee.id}">Edit</a></td>
					<td><a href="deleteEmployee?id=${employee.id}">Delete</a></td>
				</c:when>
				<c:otherwise>
					<td>Edit</td>
					<td>Delete</td>
				</c:otherwise>
				
				</c:choose>
			
			</tr>
		
		</c:forEach>
	
	</table>
	</c:when>
	<c:otherwise>No Employees available</c:otherwise>
		

	</c:choose>
	
	<a href="${pageContext.request.contextPath}/managers/employeeList/createEmployee">Add Employee</a>
	<a href="${pageContext.request.contextPath}/managers">Return to manager view</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>
