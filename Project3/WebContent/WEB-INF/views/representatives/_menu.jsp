<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<div style="padding: 5px;">
 	<c:choose>
 	<c:when test="${not empty loginedUser and loginedUser.userType=='Representative'}">
 	<a href="${pageContext.request.contextPath}/">Home</a>
   |
   <a href="${pageContext.request.contextPath}/representatives">Main Page</a>
   |
   <a href="${pageContext.request.contextPath}/representatives/clientList">Client List</a>
   |
   <a href="${pageContext.request.contextPath}/changePassword">Change Password</a>
   |
   <a href="${pageContext.request.contextPath}/logout">Logout</a>
 	
 	</c:when>
 
 <c:otherwise>
   <a href="${pageContext.request.contextPath}/">Home</a>
   |
   <a href="${pageContext.request.contextPath}/productList">Product List</a>
   |
   <a href="${pageContext.request.contextPath}/userInfo">My Account Info</a>
   |
   <a href="${pageContext.request.contextPath}/login">Login</a>
 	</c:otherwise>
    </c:choose>
</div>  