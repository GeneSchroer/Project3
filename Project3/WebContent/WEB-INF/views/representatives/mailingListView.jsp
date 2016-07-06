<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeForms.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client List</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Customer Mailing List</h3>
	<c:choose>
		<c:when test="${not empty mailingListList}">
			<table border="2" cellpadding="5" cellspacing="1">
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>Zip Code</th>
				</tr>
				<c:forEach items="${mailingListList}" var="mailingList">
				<tr>
					<td>${mailingList.firstName} ${mailingList.lastName}</td>
					<td>${mailingList.email}</td>
					<td>${mailingList.address}</td>
					<td>${mailingList.city}</td>
					<td>${mailingList.state}</td>
					<td>${mailingList.zipCode}</td>
					
				</tr>
			
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<br/>
			<p class="nonform">You have no clients</p>
			<br/>
		</c:otherwise>
	</c:choose>	
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>