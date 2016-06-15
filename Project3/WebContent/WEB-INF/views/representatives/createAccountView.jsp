<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
</head>
<body>

<form method="POST" action="doCreateAccount">
<input type="hidden" name="clientId" value="${clientId}">

Account number:
<input type="text" name="id" value="${id}"/><br>

Date Opened(yyyy:mm:dd)
<input type="text" name="dateOpened" value="${dateOpened}"/> 

Or Now:
<input type="checkbox" name="now" value="now"/><br>
<input type="submit" value="Submit"/>
<a href="${pageContext.request.contextPath}/representatives/accountList?id=${clientId}">Return to representative view</a>
</form>
</body>
</html>