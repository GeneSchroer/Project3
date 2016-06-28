<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client List</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h1>Client List</h1>
	<c:choose>
	<c:when test="${not empty clientList}">
		<table border="2" cellpadding="5" cellspacing="1">
		<tr>
			<th>Id</th>
			<th>Last Name</th>
			<th>First Name</th>
			<th>Edit</th>
			<th>Delete</th>
			<th>View Accounts</th>
		</tr>
		<c:forEach items="${clientList}" var="client">
			<tr>
				<td>${client.id}</td>
				<td>${client.lastName}</td>
				<td>${client.firstName}</td>
				<td><a href="editClient?id=${client.id}">Edit</a></td>
				<td><a href="deleteClient?id=${client.id}">Delete</a></td>
				<td><a href="accountList?id=${client.id}">Accounts</a>		
			</tr>
		
		</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<p>No clients available.</p>
	</c:otherwise>
	</c:choose>
	<a href="${pageContext.request.contextPath}/representatives/createClient">Add Client</a>
	<a href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>