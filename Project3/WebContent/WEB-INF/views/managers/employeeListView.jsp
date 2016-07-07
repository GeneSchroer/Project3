<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Employee List</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

<c:choose>
	<c:when test="${not empty employeeList}">
	<div class="tabledetails">
	<table border="2" cellpadding="5" cellspacing="1">
		<tr>
			<th>Id</th>
			<th>Last Name</th>
			<th>First Name</th>
			<th>Start Date</th>
			<th>Telephone</th>
			<th>Edit</th>
			<th>Delete</th>
			<th>Move Accounts</th>
		</tr>
		<c:forEach items="${employeeList}" var="employee">
			<tr>
				<td>${employee.id}</td>
				<td>${employee.lastName}</td>
				<td>${employee.firstName}</td>
					
				<td>
					<fmt:formatDate type="date" value="${employee.startDate}"/>
					<div class="tabledetails-content">
						<span>
							${employee.firstName} ${employee.lastName}
						</span><br/>
						<span>
							SSN: ${employee.SSN }
						</span><br/>
						<span>
							${employee.address}
						</span><br/>
						<span>
							Rate: <fmt:formatNumber type="currency" value="${employee.hourlyRate}"/>
						</span>
						
					</div>	
				</td>
				<td>${employee.telephone }</td>
				<c:choose>
				<c:when test="${employee.id!=loginedUser.id }">
					<td><a href="editEmployee?id=${employee.id}">Edit</a></td>
					<td><a href="deleteEmployee?id=${employee.id}">Delete</a></td>
					<td><a href="moveAccount?id=${employee.id}">Move</a></td>
				</c:when>
				<c:otherwise>
					<td>Edit</td>
					<td>Delete</td>
					<td>N/A</td>
				</c:otherwise>
				
				</c:choose>
				
			</tr>
		
		</c:forEach>
	
	</table>
	</div>
	</c:when>
	<c:otherwise>No Employees available</c:otherwise>
		

	</c:choose>
	
	<a class="returnbtn" href="${pageContext.request.contextPath}/managers/employeeList/createEmployee">Add Employee</a>
	<a class="returnbtn" href="${pageContext.request.contextPath}/managers">Return to manager view</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>
