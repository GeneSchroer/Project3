<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript">
		function setDefault(){
			var defaultLocation = document.getElementById("defaultLocation");
			var fileName = document.getElementById("fileName");
			if (defaultLocation.checked==true){
				fileName.disabled=true;
				fileName.required=false;
				
			}
			else if(defaultLocation.checked==false){
				fileName.disabled=false;
				fileName.required=truel
				
			}
			
		}
	
	
	</script>
	<style>
		input[type=text]:disabled{
			background-color:#444444;
			color:white;
		}
	</style>
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Backup Database</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>	
	
	
	<span style="display:inline-block; background-color: #333333; color:#cccccc; padding:4px;">
		Enter an absolute pathname (eg. C:\Users\Work\backup.sql) to create a backup of the database.
	</span> <br/>
	
	<span style="display:inline-block; background-color: #333333; color:#cccccc; padding:4px;"
	>Note: For security reasons, the file cannot already exist</span><br/>
	<form action="doBackup" method="POST">
		<p style=" display:inline-block;background-color:#333333; color:#ec2323;">${errorStrBackup }</p>
		<input type="text" id="fileName" name="fileName" value="${fileName}" required/>
		<input type="submit" value="Submit"/>
		<input type="checkbox" id="defaultLocation" name="defaultLocation" value="setToDefault" onChange="setDefault()"  />
		<span style="background-color:#444444; color: #dddddd; padding:2px;" >Or backup to C:\Users\Work</span>
	</form>
		<a class="returnbtn" href="${pageContext.request.contextPath}">Return to main page</a>
	
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>