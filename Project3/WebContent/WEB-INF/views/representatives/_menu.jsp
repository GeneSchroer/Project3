<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<div style="padding: 5px;">
 	<c:choose>
 	<c:when test="${not empty loginedUser and loginedUser.userType=='Representative'}">
 	<div class="menubar">
 	<ul>
 		
   		<li>
   			<a href="${pageContext.request.contextPath}/representatives">Main Page</a>
   		</li>
   		<li>
   			<div class="quicklinks">
   			<a >Quick Links</a>
   			<div class="quicklinks-content">
   				<a href="${pageContext.request.contextPath}/representatives/recordOrderB">Record Order</a>
   				<a href="${pageContext.request.contextPath}/representatives/clientList">Client List</a>
   				<a href="${pageContext.request.contextPath}/representatives/employeeList">Employee List</a>
   				<a href="${pageContext.request.contextPath}/representatives/mailingList">Mailing List</a>	
   				<a href="${pageContext.request.contextPath}/representatives/stockSuggestionList">Stock Suggestions</a>	
   			</div>
   			</div>
   		</li>
   		<li>
   			<a href="${pageContext.request.contextPath}/representatives/personalInfo">Personal Info</a>
   		</li>
   		<li>
   			<a href="${pageContext.request.contextPath}/changePassword">Change Password</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/logout">Logout</a>
 		</li>
 	</ul>
 	</div>
 	</c:when>
 
 <c:otherwise>
   <div class="menubar">	
   <ul>
   		<li>
   			<a href="${pageContext.request.contextPath}/">Home</a>
   		</li>
   		<li>
		   <a href="${pageContext.request.contextPath}/login">Login</a>
 		</li>
 	</ul>
 	</div>
	</c:otherwise>
	</c:choose>
</div>  