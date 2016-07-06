<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Client</title>
</head>
<body class="deleteclient">
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<c:choose>
    	<c:when test="${not empty employee and hasClients eq false }">
    		<form method="POST" action="doDeleteEmployee">
    			<input type="hidden" name="SSN" value="${employee.SSN}"/>
    			
    			<input type="hidden" name="employeeId" value="${employee.id}">
					<p class="deleteclient">Warning: Deleting this employee will delete all associated information, 
    				including all orders made by them. Are you sure you wish to proceed?</p>
    			<input class="deleteclient" type="submit" value="Delete Employee"/>
    		</form>
    	</c:when>
    	<c:when test="${not empty employee and hasClients eq true }">
    		<p>This employee is still an active broker to some client. 
    		Move clients to a different broker before deleting</p>
    	</c:when>
    	<c:otherwise>
    		Error: There is no employee to delete
    	</c:otherwise>
    </c:choose>
    
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>