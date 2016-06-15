<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
     <meta charset="UTF-8">
     <title>Home Page</title>
  </head>
  <body>
 
    <h3> Basic Home Page </h3><br>

	<a href="${pageContext.request.contextPath}/managers"> Managers</a><br>
	<a href="${pageContext.request.contextPath}/representatives">Customer Representatives</a><br>
	<a href="${pageContext.request.contextPath}/customers">Customers</a><br>
    <a href="${pageContext.request.contextPath}/debugPage">Debug Options</a><br>
 	<a href="${pageContext.request.contextPath}/loginPage">Login Page</a><br>
  </body>
</html>