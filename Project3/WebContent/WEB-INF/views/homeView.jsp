<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
<!DOCTYPE html>
<html>
  <head>
  		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginStyle.css">
  
     <meta charset="UTF-8">
     <title>Home Page</title>
  </head>
  <body>
 
    <h3> Basic Home Page </h3><br>
	<c:choose>
		<c:when test ="${not empty loginedUser and loginedUser.userType eq 'Manager'}">
			<a class="returnbtn" href="${pageContext.request.contextPath}/managers"> Managers</a><br>
		</c:when>
		<c:when test ="${not empty loginedUser and loginedUser.userType eq 'Representative'}">
			<a class="returnbtn" href="${pageContext.request.contextPath}/representatives">Customer Representatives</a><br>
		</c:when>
		<c:when test ="${not empty loginedUser and loginedUser.userType eq 'Customer'}">
			<a class="returnbtn" href="${pageContext.request.contextPath}/customers">Customers</a><br>
		</c:when>
		
 	</c:choose>
 	<a class="returnbtn" href="${pageContext.request.contextPath}/loginPage">Login Page</a><br>
  </body>
</html>