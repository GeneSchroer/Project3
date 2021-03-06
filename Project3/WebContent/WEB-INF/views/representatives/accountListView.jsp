<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

	<c:if test="${not empty client}">
	<p style="
		border: solid gold 1px;
		border-radius:3px;
		display: inline-block;
		font-size: 1.1em;
		background-color: silver;
		padding: 8px;
	">
		Name: ${client.firstName} ${client.lastName}
	</p>
	</c:if>
	<br>

	<c:choose>
		<c:when test="${not empty accountList}">
			<table borders="1" cellpadding="2">
			<tr>
				<th>Account Id</th>
				<th>Opened</th>
			</tr>
			<c:forEach items="${accountList}" var="account">
			<tr>
				<td>${account.id}</td>
				<td><fmt:formatDate type="date" value="${account.dateOpened}"/></td>
			</tr>			
			</c:forEach>
		</table>
		
		</c:when>
		<c:otherwise>
			<p style="
			background-color: black;
			color: white;
			display:inline-block;
			font-size:1.05em;
			padding: 5px;">
			No accounts available
			</p>
		</c:otherwise>
	</c:choose>
	<br>
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives/createAccount?id=${client.id}">Create new account</a>
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives/clientList">Return to client list</a>
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>