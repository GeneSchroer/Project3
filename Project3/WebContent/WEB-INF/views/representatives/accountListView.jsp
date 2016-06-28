<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

	<c:if test="${not empty client}">
	Name: ${client.firstName} ${client.lastName}
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
		<c:otherwise>No accounts available</c:otherwise>
	</c:choose>
	<br>
	<a href="${pageContext.request.contextPath}/representatives/createAccount?id=${client.id}">Create new account</a>
	<a href="${pageContext.request.contextPath}/representatives/clientList">Return to client list</a>
	<a href="${pageContext.request.contextPath}/representatives">Return to representative view</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>