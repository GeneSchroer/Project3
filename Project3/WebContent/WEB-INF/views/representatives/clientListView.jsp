<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client List</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Client List</h3>
	<c:choose>
	<c:when test="${not empty clientList}">
		<div class="tabledetails">
		<table border="2" cellpadding="5" cellspacing="1">
		<tr>
			<th>Id</th>
			<th>Last Name</th>
			<th>First Name</th>
			<th>Telephone</th>
			<th>Edit</th>
			<th>Delete</th>
			<th>View Accounts</th>
		</tr>
		<c:forEach items="${clientList}" var="client">
			<tr>
				<td>${client.id}</td>
				<td>${client.lastName}</td>
				<td>
					${client.firstName}
					<div class="tabledetails-content">
						<span>
							${client.firstName} ${client.lastName}
						</span><br/>
						<span>
							${client.address}
						</span><br/>
						<span>
							Rating: ${client.rating}
						</span><br/>
						<span>
							Credit Card: ${client.creditCardNumber}
						</span><br/>
						<span>
							Email: ${client.email}
						</span>
					</div>
					
				</td>
				<td>${client.telephone }</td>
				<td><a href="editClient?id=${client.id}">Edit</a></td>
				<td><a href="deleteClient?id=${client.id}">Delete</a></td>
				<td><a href="accountList?id=${client.id}">Accounts</a>		
			</tr>
		
		</c:forEach>
		</table>
		</div>
	</c:when>
	<c:otherwise>
		<br/>
		<div class="notavailable"><span>You do not have any clients</span></div>
		<br/>
		<br/>
	</c:otherwise>
	</c:choose>
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives/createClient">Add Client</a>
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>