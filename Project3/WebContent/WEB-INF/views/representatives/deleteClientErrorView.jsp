<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 <head>
 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
    <meta charset="UTF-8">
    <title>Delete Client</title>
 </head>
 
 <body>
    
    <h3>Delete Client</h3>
    
    <p style="color: red;">${errorString}</p>
    <a href="clientList">Client List</a>
    
    
 </body>
</html>