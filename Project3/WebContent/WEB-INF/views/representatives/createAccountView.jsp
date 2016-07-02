<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
<script type="text/javascript">
	function chooseDate(){
		if(document.getElementById("now").checked==true)
			document.getElementById("dateOpened").disabled=true;
		else
			document.getElementById("dateOpened").disabled=false;
	}
</script>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<form method="POST" action="doCreateAccount">
		<input type="hidden" name="clientId" value="${clientId}">

	<%--
	<p style="color: red;">${errorStrId}</p>
	Account number:
	<input type="text" name="id" value="${id}"/><br>
	--%>
	
	<p style="color: red;">${errorStrDateOpened}</p>
	Date Opened(yyyy-mm-dd)
	<input type="text" id = "dateOpened" name="dateOpened" value="${dateOpened}"/> 
	
	Or Now:
	<input type="checkbox" id="now" name="now" value="now" onchange="chooseDate()"/><br>
	<input type="submit" value="Submit"/>

	</form>
		<a href="${pageContext.request.contextPath}/representatives/accountList?id=${clientId}">Return to representative view</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>