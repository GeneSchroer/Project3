<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
  <div style="float: left">
     <h1>Customer Transactions</h1>
  </div>
 
  <div style="float: right; padding: 10px; text-align: right;">
 
     <!-- User store in session with attribute: loginedUser -->
     <c:if test="${ not empty loginedUser}">
     Hello <b>${loginedUser.userName}</b>
   <br/>
   
   
   <form method="POST" action="${pageContext.request.contextPath}/customers/doSearchStocks">
    Search <input type = "search" name="search">
    </form>
    
    
   </c:if>
  </div>
 
</div>