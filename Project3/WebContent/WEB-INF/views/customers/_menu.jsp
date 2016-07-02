<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    

 	<c:choose>
 	<c:when test="${not empty loginedUser and loginedUser.userType=='Customer'}">
 	<ul class="menulist">
   		<li class="menulist">
   			<a class="menulist" href="${pageContext.request.contextPath}/">Home</a>
   		</li>
   		<li class="menulist">
   			<a class="menulist" href="${pageContext.request.contextPath}/customers">Main Page</a>
   		</li>
   		<li class="menulist">
	   		<div id="menubar">
	   			<a class="menulist">Quick Links</a>
	   		<div id="menubar-content">
	   			<a class="menuopt" href="${pageContext.request.contextPath}/customers/stockHolding">Portfolio</a><br/>
				<a class="menuopt" href="${pageContext.request.contextPath}/customers/stockHistory">Stock History</a><br/>
				<a class="menuopt" href="${pageContext.request.contextPath}/customers/stockList">Stock List</a><br/>
				<a class="menuopt" href="${pageContext.request.contextPath}/customers/suggestedStock">Suggested Stocks</a><br/>
	   		</div>
	   		</div>
 		</li>
	   	<li class="menulist">
	   		<a class="menulist" href="${pageContext.request.contextPath}/customers/orderList">View Orders</a>
	   	</li>
   		<li class="menulist">
   			<a class="menulist" href="${pageContext.request.contextPath}/changePassword">Change Password</a>
   		</li>
	   	<li class="menulist">
	   		<a class="menulist" href="${pageContext.request.contextPath}/logout">Logout</a>
		</li>
	</ul>
	</c:when>
	<c:otherwise>
	</c:otherwise>
    </c:choose>
