<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<div style="padding: 10px; align:left;">
 	
 	<div class="menubar">
	<ul>
		<li>
   			<a href="${pageContext.request.contextPath}/">Home</a>
   		</li>
   		<li>
   			<a href="${pageContext.request.contextPath}/managers">Main Page</a>
   		</li>
   		<li>
   			<a class="quicklinks">Quick Links</a>
   			<div class="quicklinks-content">				
   			
   				<a href="${pageContext.request.contextPath}/managers/employeeList">Employee List</a>
   				<a href="${pageContext.request.contextPath}/managers/salesReport">Sales Reports</a>
   				<a href="${pageContext.request.contextPath}/managers/stockList">Stock List</a>
   				<a href="${pageContext.request.contextPath}/managers/orderList"> list of orders</a>  					
   				<a href="${pageContext.request.contextPath}/managers/summaryListing">Summary Listing</a>
   			
   			</div>
   		</li>
   		<li>
   			<a href="${pageContext.request.contextPath}/changePassword">Change Password</a>
   		</li>
		<li>
			<a href="${pageContext.request.contextPath}/logout">Logout</a>
		</li>
		</ul>
	</div>
</div>  