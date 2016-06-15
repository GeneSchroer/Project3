<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	 <form method="POST" action="doEditStock">
	 <p style="color:red;">Stock Symbol: ${stockSymbol}</p>
	 <p style="color:red;">Price Per Share: ${pricePerShare}</p>
	<input type="hidden" name="stockSymbol" value="${stockSymbol}"/>
	<input type="hidden" name="pricePerShare" value="${pricePerShare}"/>
	
	
	<!-- Company Name -->
	<p style="color: red;">${errorStrCompanyName}</p> 
	Company Name:<br>
	<input type="text" name="companyName" value="${companyName}"><br>
	
	<!-- Type -->
	<p style="color: red;">${errorStrType}</p> 
	Stock Type:<br>
	<input type="text" name="type" value="${type}"><br>
	  
	  
	<input type="submit" value="Submit"/>
  	<a href="${pageContext.request.contextPath}/managers/stockList">Return to stock list</a>
	
	</form>
</body>
</html>