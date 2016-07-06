<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
       <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
       
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	
	td, th{
		padding:4px;
		border:solid black 1px;
	}

</style>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<c:if test="${not empty employee}">
		<table>
			<tr>
				<th>Name</th>
				<th>Address</th>
				<th>City</th>
				<th>State</th>
				<th>ZipCode</th>
				<th>Telephone</th>
				<th>Start Date</th>
				<th>Hourly Rate</th>
			</tr>
			<tr>
				<td>${employee.firstName} ${employee.lastName}</td>
				<td>${employee.address}</td>
				<td>${location.city}</td>
				<td>${location.state}</td>
				<td>${location.zipCode}</td>
				<td>${employee.telephone}</td>
				<td>
					<fmt:formatDate type="date" value="${employee.startDate}"/>
				</td>
				<td>
					<fmt:formatNumber type="currency" value="${employee.hourlyRate}"/>
				</td>
			</tr>
		</table>
	</c:if>
	  <a class="returnbtn" href = "${pageContext.request.contextPath}/managers">Return to Main Page</a>
	
		<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>