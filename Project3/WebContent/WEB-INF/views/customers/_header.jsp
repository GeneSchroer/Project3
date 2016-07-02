<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
<div style="background: #E0E0E0; height: auto; padding: auto; width:auto;">
  
     <span style="font-size:250%">Customer Transactions</span>
     <div style="float: right">
      <!-- User store in session with attribute: loginedUser -->
     <c:if test="${ not empty loginedUser}">
     Hello <b>${loginedUser.userName}</b><br/>
  
     <form method="POST" action="${pageContext.request.contextPath}/customers/doSearchStocks">
    <label for="search"> Stock Search:</label> <input id="search" type = "search" name="search" placeholder="Search">
    </form>
    
   
 
    
   </c:if>
     
  </div>
    
 
</div>