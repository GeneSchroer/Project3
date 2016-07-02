<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Stock</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Create New Stock</h3>

	<form method="POST" action="doCreateStock">

	<%-- Stock Symbol --%>
	<div class="managerform">
	<ul>
	<li>
		<span class="errordetails">
			${errorStrStockSymbol}
		</span>
	    <span>
	    	Symbol
	    </span>
    	<input type="text" name="stockSymbol" value="${stockSymbol}" required/>
	</li>
	
	<%-- Company Name --%>
	<li>
		<span class="errordetails">
			${errorStrCompanyName}
		</span>
		<span style="width:120px;">
    		Company Name
    	</span>
    	<input type="text" name="companyName" value="${companyName}" required/>
	</li>
	
	<%-- Type --%>
  	<li>
  		<span class="errordetails">
  			${errorStrType}
  		</span>
  		<span>
  			Stock Type
  		</span>
  		<input type="text" name="type" value="${type}" required/>
	</li>	
	
	<%-- Price Per Share --%>
	<li>
	<span class="errordetails">
		${errorStrPricePerShare}
	</span>
  	<span>
 	 	Share Price
  	</span>
  	<input type="text" name="pricePerShare" value="${pricePerShare}" required/>
	</li>
	
	<%-- Num Shares--%>
	<li>
		<span class="errordetails">
			${errorStrNumShares}
		</span>
  		<span style="width:120px;">
  			Shares Available
  		</span>
  		<input type="text" name="numShares" value="${numShares}" required>
	</li>
	<li>
		<input type="submit" value="Submit"/>
	</li>
	</ul>
	</div>
	</form>
	<a class="returnbtn" href = "${pageContext.request.contextPath}/managers/stockList">Return to Stock List</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>