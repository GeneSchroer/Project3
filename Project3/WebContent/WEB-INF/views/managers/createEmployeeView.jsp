<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
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
	<div class="managerform">
	<ul>
	
	<li>
	
		<span class="errordetails" style="color: red;">
	  		${errorStrEmployeeType}
	  	</span>
		<span>
			Type
			<span class="managertooltip">
				Create either a manager or customer representative
			</span> 
		</span>
		<select name="userType" >
			<option value="Representative">Representative</option>
		  	<option value="Manager">Manager</option>
		</select>
	  
	  	
	 </li> 
	 
	 <%-- First Name --%>
	<li>
	<span class="errordetails" >
	 	${errorStrFirstName}
	</span>
	<span>
		First Name
		<span class="managertooltip">
			First name of employee
		</span>
	</span>
	<input type="text" name="firstName" value="${firstName}" required>	
	</li> 
	  
  
	<%-- Last Name --%>
	<li >
		<span class="errordetails" style="color: red;">
	 		${errorStrLastName}
		</span>
		<span >
	  		Last Name
	  		<span class="managertooltip">
	  			Last name of employee
	  		</span>
	  	</span>
	  	<input type="text" name="lastName" value="${lastName}" required>	
	</li>
	  
	
	<%-- User Name --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrUserName}
	</span>
	<span>
		User Name
		<span class="managertooltip">
			User name of employee
		</span>
	</span>
	  <input type="text" name="userName" value="${userName}" required>
	  
	</li>
	
	<%-- Password --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrPassword}
	</span>
	<span>
		Password
		<span class="managertooltip">
			A temporary password for an employee
		</span>
	</span>
	<input type="text" name="password" value="${password}" required>
	</li>
	  
	<%-- SSN --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrSSN}
	</span>
	<span>
		SSN
		<span class="managertooltip">
			Employee's social security number
		</span>
	</span>
	<input type="text" name="SSN" min="1" max="999999999" value="${SSN}" required>
	</li>
	
	<%-- Address --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrAddress}
	</span>
	<span >
		Address
		<span class="managertooltip">
			Employee address
		</span>
	</span>
	<input type="text" name="address"value="${address}" required>
	</li>
	  
	<%-- City --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrCity}
	</span>
	<span >
		City
		<span class="managertooltip">
			Employee's city of residence
		</span>
	</span>
	<input type="text" name="city"value="${city}" required>
	
	</li>  
	  
	<%-- State --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrState}
	</span>
	<span>
		State
		<span class="managertooltip">
			Employee's state of residence
		</span>
	</span>
	<select name="state">
		<jsp:include page="_state.jsp"></jsp:include>
	</select>
	</li>
	
	<%-- Zip Code --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrZipCode}
	</span>
	<span >
		Zip Code
		<span class="managertooltip">
			Employee's zip code
		</span>
	</span>
	<input type="text" name="zipCode" min="1" max="99999"value="${zipCode}" required placeholder="(#####)">
	
	</li>
	 
	<%-- Telephone --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrTelephone}
	</span>
	<span >
		Telephone
		<span class="managertooltip">
			Employee's telephone number
		</span>
	</span>
	<input type="text" name= "telephone" min="1" max="9999999999" value="${telephone}" required placeholder="(###-###-####)">
	 
	
	</li>  
    
    <%-- Start Date --%>
    <li>
    <span class="errordetails">
		${errorStrStartDate}
	</span>
    <span >
    	Start Date
    	<span class="managertooltip">
    		Starting date for employee
    	</span>
    </span>	
	<input type="text" id="startDate" name="startDate" value="${startDate}" placeholder="(YYYY-MM-DD)" required/>
	<input style="margin-left:175px;" type="checkbox" id="now" name="now" onchange="chooseDate()" value="date is now"/>
	<span style="margin-left: 200px; width: 80px;">
		Or Now
		<span class="managertooltip">
			Set the start date to today
		</span>
    </span>
	</li>
	  
	<%-- Hourly Rate --%>
	<li >
	<span class="errordetails" style="color: red;">
		${errorStrHourlyRate}
	</span>
	<span>
		Hourly Rate
		<span class="managertooltip">
			Hourly rate of employee
		</span>
	</span>
	<input type="number" name="hourlyRate" min="1" value="${hourlyRate}" required>  
	
  </li>
  <li >
  	<input type="submit" value="Submit"/>
  </li>
  </ul>
  </div>
  <a class="returnbtn" href = "${pageContext.request.contextPath}/managers/employeeList">Return to employee list</a>
</form>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>