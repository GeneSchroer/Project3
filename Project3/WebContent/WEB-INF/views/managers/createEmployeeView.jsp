<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Employee</title>
<script type="text/javascript">
	function chooseDate(){
		if (document.getElementById("now").checked==true)
			document.getElementById("startDate").disabled = true;
		else 
			document.getElementById("startDate").disabled = false;
	}

</script>

</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

	<h3>Create Employee:</h3>
	<p style="color: red;">${errorString}</p>
	<form method="POST" action="doCreateEmployee">
  
  	<!-- Employee Type -->
	  <p style="color: red;">${errorStrEmployeeType}</p>
	  Employee Type:<br>
	  <select name="userType" >
	  	<option value="Representative">Representative</option>
	  	<option value="Manager">Manager</option>
	  </select><br>
	  <!-- First Name -->
	  <p style="color: red;">${errorStrFirstName}</p>
	  First name:<br>
	  <input type="text" name="firstName" value="${firstName}"><br>
  
	  <!-- Last Name -->
	  <p style="color: red;">${errorStrLastName}</p>
	  Last name:<br>
	  <input type="text" name="lastName" value="${lastName}"><br>
	
	  <!-- User Name -->
	  <p style="color: red;">${errorStrUserName}</p>
	  User Name:<br>
	  <input type="text" name="userName" value="${userName}"><br>
	  
	  <!-- Password -->
	  <p style="color: red;">${errorStrPassword}</p>
	  Password:<br>
	  <input type="text" name="password" value="${password}"><br>

	  <!-- SSN -->
	  <p style="color: red;">${errorStrSSN}</p>
	  SSN:<br>
	  <input type="number" name="SSN" min="1" max="999999999" value="${SSN}"><br>
	
	  <!-- Address -->
	  <p style="color: red;">${errorStrAddress}</p>
	  Address:<br>
	  <input type="text" name="address"value="${address}"><br>
	  
	  <!-- City -->
	  <p style="color: red;">${errorStrCity}</p>
	  City:<br>
	  <input type="text" name="city"value="${city}"><br>
	  
	  <!-- State -->
	  <p style="color: red;">${errorStrState}</p>
	  State:<br>
	  <input type="text" name="state"value="${state}"><br>
  
	  <!-- Zip Code -->
	  <p style="color: red;">${errorStrZipCode}</p>
	  Zip Code:<br>
	  <input type="number" name="zipCode" min="1" max="99999"value="${zipCode}"><br>
	  
	  <!-- Telephone -->
	  <p style="color: red;">${errorStrTelephone}</p>
	  Telephone:<br>
	  <input type="text" name= "telephone" min="1" max="9999999999" value="${telephone}"><br>
	  
    	<input type="checkbox" id="now" name="now" onchange="chooseDate()" value="date is now"/>Choose present date<br> 
  
	  <!-- Start Date -->
	  <p style="color: red;">${errorStrStartDate}</p>
	  Start Date (yyyy-mm-dd):<br>
	  <input type="date" id = "startDate" name="startDate" value="${startDate}"><br>
	  
	  <!-- Hourly Rate -->
	  <p style="color: red;">${errorStrHourlyRate}</p>
	  Hourly Rate<br>  
	  <input type="number" name="hourlyRate" min="1" value="${hourlyRate}"><br>

  <input type="submit" value="Submit"/>
  <a href = "${pageContext.request.contextPath}/managers/employeeList">Return to employee list</a>
</form>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>