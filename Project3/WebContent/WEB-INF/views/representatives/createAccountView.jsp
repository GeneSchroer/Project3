<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeForms.css">
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
	<div class="managerform">
	<ul>
		<li>
			<span class="errordetails">
				${errorStrDateOpened}
			</span>
			<span style="width:130px; margin-bottom: 5px;">
				Date Opened
			</span>
			<input type="text" id = "dateOpened" name="dateOpened" value="${dateOpened}" placeholder="YYYY-MM-DD"/> 
		</li>
		<li>
			<span style=" width:120px;height:30px;">
				Or Now
			
				<input type="checkbox" id="now" name="now" value="now" onchange="chooseDate()"/><br>
			</span>
		</li>
		<li>
			<input type="submit" value="Submit"/>
		</li>
	</form>
		<a class="returnbtn" href="${pageContext.request.contextPath}/representatives/accountList?id=${clientId}">Return to representative view</a>
    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>