<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/customerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<script language="javascript" type="text/javascript">
	function validate(){
		var fromDate=document.getElementById("fromDate").value;
		var toDate=document.getElementById("toDate").value;
		var errorFrom = document.getElementById("errorStrFromDate");
		var errorTo = document.getElementById("errorStrToDate");
		var isValid=true;
		if(isNaN(Date.parse(fromDate))){
			errorFrom.innerHTML="Error: " + fromDate + " is not a valid date!"
			isValid=false;	}
		else
			errorFrom.innerHTML="";
		
		if(isNaN(Date.parse(toDate))){
			errorTo.innerHTML="Error: " + toDate +" is not a valid date!";
			isValid=false;	 }
		else
			errorTo.innerHTML="";
		
		if(isValid == true)
			if (Date.parse(fromDate) > Date.parse(toDate)){
				 errorFrom.innerHTML="Error: From Date cannot be *after* To Date!";
				 isValid=false;  }
			else
				errorFrom.innerHTML="";
	
		return isValid;
	}
</script>

<jsp:include page="_header.jsp"></jsp:include>
</head>
<body>
	<nav>
    <jsp:include page="_menu.jsp"></jsp:include>
	</nav>
	<%--Form to select a stock and choose which one to use--%>
	<c:choose>
		<c:when test="${not empty haveStocks}">
			<form method="POST" action="doStockHistory" onsubmit="return validate()">
			<span>Select Stock:</span>			
			<select name="stockSymbol">
				<c:forEach items="${stockList}" var="stock">
					<option value="${stock.stockSymbol}">${stock.stockSymbol} ${stock.companyName}</option>
			
				</c:forEach>
			</select><br>
			<p>Date (YYYY-MM-DD)</p>
			
			<%--From Date --%>
			From:<br/>
			<span id="errorStrFromDate" style="color: red;">${errorStrFromDate}</span><br/>
			<input id="fromDate" type="text" name="fromDate" value="${fromDate}" required/><br>
			
			<%--To Date --%>
			To:<br/>			
			<span id="errorStrToDate" style="color: red;">${errorStrToDate}</span><br/>
			<input id="toDate" type="text" name="toDate" value="${toDate}" required/>
			<input type="submit" value="Submit"/>
			</form>
			
			
			<c:if test="${not empty stockHistoryList}">
				<table border="2">
				<tr>
					<th>Date Time:</th>
					<th>Price Per Share:</th>
				</tr>
				<c:forEach items="${stockHistoryList}" var="stockHistory">
				<tr>				
					<td>
						<fmt:formatDate type="both" value="${stockHistory.dateTime}"/>
					</td>
					<td>${stockHistory.pricePerShare} </td>
				</tr>
				</c:forEach>
				</table>
			
			</c:if>
			
		</c:when>
		
		<c:otherwise> There's no stocks available</c:otherwise>
		
	</c:choose>
		<a class="returnbtn" href="${pageContext.request.contextPath}/customers">Return to customer menu</a>
	
	
</body>
<jsp:include page="_footer.jsp"></jsp:include>
</html>