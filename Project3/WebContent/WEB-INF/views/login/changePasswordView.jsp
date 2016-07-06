<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Change Password</h3>
	<form method="POST" action="doChangePassword">
		<div class="loginpage">
		<table>
		<tr>
			<td>
				<p style="color: red;">${errorStrPassword}</p>
			</td>
		</tr>
		<tr>
			<td>
				<span style="width:150px;">
					Change Password
				</span> 
			</td>
			<td>
				<input type="text" name="password1" value="${password1}" required>
			</td>
		</tr>
		<tr>
			<td>
				<span style="width:150px;">
					Repeat Password
				</span>
			</td>
			<td>
				<input type="text" name="password2" value="${password2}" required>
			</td>
		</tr>
		</table>
		
		<input type="submit" value="Submit"/>
	</div>
	</form>
	<a class="returnbtn" href="${pageContext.request.contextPath}/customers/orderList">Return to Order List</a>
    <jsp:include page="_footer.jsp"></jsp:include>
	
</body>
</html>