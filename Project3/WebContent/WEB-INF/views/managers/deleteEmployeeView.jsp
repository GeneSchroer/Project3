<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Client</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<c:choose>
    	<c:when test="${not empty employeeId }">
    <form method="POST" action="doDeleteEmployee">
    <input type="hidden" name="employeeId" value="${employeeId}">
	<p>Warning: Deleting this employee will delete all associated information, 
    including all orders made by them. Are you sure you wish to proceed?</p>
    <input type="submit" value="Delete Employee"/>
    </form>
    </c:when>
    <c:otherwise>
    	No Employee to delete
    
    </c:otherwise>
    </c:choose>
    
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>