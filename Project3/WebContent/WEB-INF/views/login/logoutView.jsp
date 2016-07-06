<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginStyle.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Logging Out</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<p>You have been successfully logged out.</p><br>

	<a class="returnbtn" href="${pageContext.request.contextPath}/loginPage">Return to Login Page</a><br>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>