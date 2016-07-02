<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit Stock</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	 <h3>Edit Stock Name</h3>
	 <c:choose>
	 	<c:when test="${not empty stockSymbol}">
	 		<form method="POST" action="doEditStock">
			<input type="hidden" name="stockSymbol" value="${stockSymbol}"/>
			<input type="hidden" name="pricePerShare" value="${pricePerShare}"/>
	
	 		<div class="managerform">
	 		<ul>
	 		<li>
	 			<span style="color:red; width:150px; margin-bottom: 10px;">Stock Symbol: ${stockSymbol}</span>
			</li>
			<li>
				<span style="color:red; width:150px">
					Share Price: 
					<fmt:formatNumber type="currency" value="${pricePerShare}"/>
					</span>
			</li>
	
	
			<%-- Company Name --%>
			<li>
				<span class="errordetails">
					${errorStrCompanyName}
				</span> 
				<span style="width:120px;">
					Company Name:
				</span>
				<input type="text" name="companyName" value="${companyName}" required>
			</li>
			
			<%-- Type --%>
			<li>
				<span class="errordetails">
					${errorStrType}
				</span> 
				<span>
					Stock Type:
				</span>
				<input type="text" name="type" value="${type}" required>
	  		</li>
	  		<li>
				<input type="submit" value="Submit"/>
			</li>
		</ul>
		</div>
		</form>
	</c:when>
	<c:otherwise>
		<p>No stock chosen.</p>
	</c:otherwise>
	</c:choose>
	<a class="returnbtn" href="${pageContext.request.contextPath}/managers/stockList">Return to stock list</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>