<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
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
	<ul class="employeedetails">
	
	<li class="employeedetals">
		<span class="employeedetails">Type
		
		<span class="employeetooltip">Create either another manager or customer representative</span>
		
		</span>  
		
		
		<select class="employeedetails" name="userType" >
			<option value="Representative">Representative</option>
		  	<option value="Manager">Manager</option>
		</select>
	  
	  	<span class="errordetails" style="color: red;">
	  		${errorStrEmployeeType}
	  	</span>
	 </li> 
	 
	 <%-- First Name --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
	 	${errorStrFirstName}
	</span>
	<span class="employeedetails">
		First Name
	</span>
	<input class="employeedetails" type="text" name="firstName" value="${firstName}" required>	
	</li> 
	  
  
	<%-- Last Name --%>
	<li class="employeedetails">
		<span class="errordetails" style="color: red;">
	 		${errorStrLastName}
		</span>
		<span class="employeedetails">
	  		Last Name
	  	</span>
	  	<input class="employeedetails" type="text" name="lastName" value="${lastName}" required>	
	</li>
	  
	
	<%-- User Name --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrUserName}
	</span>
	<span class="employeedetails">
		User Name
	</span>
	  <input class="employeedetails" type="text" name="userName" value="${userName}" required>
	  
	</li>
	
	<%-- Password --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrPassword}
	</span>
	<span class="employeedetails">
		Password
	</span>
	<input class="employeedetails" type="text" name="password" value="${password}" required>
	</li>
	  
	<%-- SSN --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrSSN}
	</span>
	<span class="employeedetails">
		SSN
	</span>
	<input class="employeedetails" type="text" name="SSN" min="1" max="999999999" value="${SSN}" required>
	</li>
	
	<%-- Address --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrAddress}
	</span>
	<span class="employeedetails">
		Address
	</span>
	<input class="employeedetails" type="text" name="address"value="${address}" required>
	

	</li>
	  
	<%-- City --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrCity}
	</span>
	<span class="employeedetails">
		City
	</span>
	<input class="employeedetails" type="text" name="city"value="${city}" required>
	
	</li>  
	  
	<%-- State --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrState}
	</span>
	<span class="employeedetails">
		State
	</span>
	<select class="employeedetails" name="state">
		<jsp:include page="_state.jsp"></jsp:include>
	</select>
	</li>
	
	<%-- Zip Code --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrZipCode}
	</span>
	<span class="employeedetails">
		Zip Code
	</span>
	<input class="employeedetails" type="text" name="zipCode" min="1" max="99999"value="${zipCode}" required placeholder="(#####)">
	
	</li>
	 
	<%-- Telephone --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrTelephone}
	</span>
	<span class="employeedetails">
		Telephone
	</span>
	<input class="employeedetails" type="text" name= "telephone" min="1" max="9999999999" value="${telephone}" required placeholder="(###-###-####)">
	 
	
	</li>  
    
    <%-- Start Date --%>
    <li>
    <span class="errordetails" style="color: red;">
		${errorStrStartDate}
	</span>
    <span class="employeedetails">
    	Start Date
    </span>	
	<input class="employeedetails" type="text" id="startDate" name="startDate" value="${startDate}" placeholder="(YYYY-MM-DD)" required/>
	
	<input type="checkbox" id="now" name="now" onchange="chooseDate()" value="date is now"/>
	<label for="now">(now)</label>
    
	</li>
	  
	<%-- Hourly Rate --%>
	<li class="employeedetails">
	<span class="errordetails" style="color: red;">
		${errorStrHourlyRate}
	</span>
	<span class="employeedetails">
		Hourly Rate
	</span>
	<input class="employeedetails" type="number" name="hourlyRate" min="1" value="${hourlyRate}" required>  
	
  </li>
  <li class="employeedetails">
  	<input class="employeedetails" type="submit" value="Submit"/>
  </li>
  </ul>
  <a class="returnbtn" href = "${pageContext.request.contextPath}/managers/employeeList">Return to employee list</a>
</form>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>