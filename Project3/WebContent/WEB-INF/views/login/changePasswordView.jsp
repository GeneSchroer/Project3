<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Change Password</h3>
	<form method="POST" action="doChangePassword">
		<p style="color: red;">${errorStrPassword}</p>
		Change Password:<input type="text" name="password1" value="${password1}">
		Repeat Password:<input type="text" name="password2" value="${password2}">
	
	<input type="submit" value="Submit"/>
	</form>
	<a href="${pageContext.request.contextPath}/customers/orderList">Return to Order List</a>
    <jsp:include page="_footer.jsp"></jsp:include>
	
</body>
</html>