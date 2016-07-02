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
    	<c:when test="${not empty clientId }">
    <form method="POST" action="doDeleteClient">
    <input type="hidden" name="clientId" value="${clientId}">
    
    <p>Warning: Deleting this client will delete all information associated with him/her, 
    including orders and stock portfolios. Are you sure you wish to proceed?</p>
    
    
    <input type="submit" value="Delete Client"/>
    </form>
    </c:when>
    <c:otherwise>
    	No client to delete
    
    </c:otherwise>
    </c:choose>
    
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>