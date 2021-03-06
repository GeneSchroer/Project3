<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
 <head>
 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
  	<meta charset="UTF-8">
  	<title>Edit Employee</title>
 </head>
<body onload="setState()">
	<script type="text/javascript">
		function setState(){
			var currentState= document.getElementById("currentState").value;
			var stateSelect = document.getElementById("state");
			var states = stateSelect.options;
			for(i=0;i<states.length;i++){
				if(states[i].value==currentState){
					stateSelect.selectedIndex=i;
					return;
				}
			}
			
		}
	</script>


	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	
	<h3>Edit Employee</h3>

	<c:if test="${not empty employee}">
	<form method="POST" action="doEditEmployee">
	
	<%--Hidden values --%>
	<input type="hidden" id="currentState" value="${state}"/>
	<input type="hidden" name="SSN" value="<c:out value="${SSN}"/>">
	<input type="hidden" name="id" value="${id}"/>
 	<input type="hidden" name="startDate" value="${startDate}"/>
 	
 
	<div class="managerform">
	<ul>
		<li>  
			<span style="color:brown; width:150px;margin-bottom:5px;" >
				Employee Id: ${id}
			</span>
	  	</li>
	  	<li>
	  		<span style="color: brown; width:150px; margin-bottom:5px;" >
	  			SSN: ${SSN}
	  		</span>
	  	</li>
	  	<li>
		  <span style="color: brown; width:275px; margin-bottom: 5px;">
			  Start Date: <fmt:formatDate type="both" value="${startDate}"/>
		  </span>
		</li>
		<li>  
			<p style="color: red;">${errorStrFirstName}</p> 
	  	</li>
	  	
	  	<%--First Name --%>
	  	<li>
	  		<span class="errordetails">
	  			${errorStrFirstName}
	  		</span>
	  		<span>
	  			First Name
	  			<span class="managertooltip">
	  				First name of employee
	  			</span>
	  		</span>
	 	 	<input type="text" name="firstName" value="${firstName}"required>	
	  	</li>
	  	
	  	<%--Last Name --%>
	  	<li>
	  		<span class="errordetails" style="color: red;">
	  			${errorStrLastName}
	  		</span>
	  		<span>
	  			Last Name
	  			<span class="managertooltip">
	  				Last name of employee
	  			</span>
	  		</span>
	  		<input type="text" name="lastName" value="${lastName}" required>
  	  	</li>
  	  	
  	 	<%--Address --%>
  	 	<li>
			<span class="errordetails"style="color: red;">
	  	  		${errorStrAddress}
	  	  	</span>
  	  
  	  		<span>
  	  			Address
  	  			<span class="managertooltip">
  	  				Employee's place of residence
  	  			</span>
  	  		</span>
  	  		<input type="text" name="address"value="${address}" required>
		</li>  
  	  
  	  	<%-- City --%>
  	  	<li>
  	  		<span class="errordetails" style="color: red;">
  	  			${errorStrCity}
  	  		</span>
  	  		<span>
  	  			City
  	  			<span class="managertooltip">
  	  				Employee's city of residence
  	  			</span>
  	  		</span>
  	  		
  	  		<input type="text" name="city" value="${city}" required>
  	  	</li>
  	  	
  	  	<%-- State --%>
  	  	<li>
	  	  	<span class="errordetails" style="color: red;">
	  	  		${errorStrState}
	  	  	</span>
	  	  	<span>
	  	  		State
	  	  		<span class="managertooltip">
	  	  			Employee's city of residence
	  	  		</span>
	  	  	</span>
		  	<select id="state" name="state">
		  		<jsp:include page="_state.jsp"></jsp:include>
		  	</select>
  		</li>
  		
  		<%--Zip Code --%>
  		<li>
	  	  	<span class="errordetails" style="color: red;">
	  	  		${errorStrZipCode}
	  	  	</span>
	  	  	<span>
	  	  		Zip Code
	  	  		<span class="managertooltip">
	  	  			Employee's zipcode
	  	  		</span>
	  	  	</span>
	  	  	<input type="number" name="zipCode" min="1" max="99999"value="${zipCode}" required>
		</li>
		<%--Telephone --%>  
		<li>
 	 		<span class="errordetails" style="color: red;">
 	 	  		${errorStrTelephone}
 	 	  	</span>
  	  		<span>
  	  			Telephone
  	  			<span class="managertooltip">
  	  				Employee's telephone number
  	  			</span>
			</span>
  	  		<input type="text" name= "telephone" value="${telephone}"placeholder="###-###-####" required>
  		</li>
  		
  		<%--Hourly Rate --%>
 		<li>
 			<span class="errordetails"style="color: red;">
 				${errorStrHourlyRate}
 			</span>
  	  		<span>
  	  			Hourly Rate
  	  			<span class="managertooltip">
  	  				Employee's hourly rate
  	  			</span>
  	  		</span>  
	  		<input type="number" name="hourlyRate" min="1" value="${hourlyRate}" required>
		</li>
		
		<%--Submit Button --%>
  	  	<li>
  	  		<input type="submit" value="Submit"/>
  	  	</li>
  	  </ul>
  	  </div>
	</form>
	
	</c:if>
	<a class="returnbtn" href="${pageContext.request.contextPath}/managers/employeeList">Return to employee list</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>