<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<div style="padding: 5px; text-align:center;">
 	<c:choose>
 	<c:when test="${not empty loginedUser and loginedUser.userType=='Customer'}">
 	
   <a href="${pageContext.request.contextPath}/">Home</a>
   |
   <a href="${pageContext.request.contextPath}/customers">Main Page</a>
   |
   <a href="${pageContext.request.contextPath}/customers/stockHolding">Portfolio</a>
   |
   <a href="${pageContext.request.contextPath}/customers/orderList">View Orders</a>
   |
   <a href="${pageContext.request.contextPath}/changePassword">Change Password</a>
   |
   <a href="${pageContext.request.contextPath}/logout">Logout</a>
	</c:when>
	<c:otherwise>
	</c:otherwise>
    </c:choose>
</div>  