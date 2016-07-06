<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeForms.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit Personal Info</title>
</head>
<body onload="setState()">
	<script type="text/javascript">
		function setState(){
			var currentState= document.getElementById("currentState").value;
			var stateSelect = document.getElementById("state");
			var states = stateSelect.options;
			for(i=0;i<states.lengthjm;i++){
				if(states[i].value==currentState){
					stateSelect.selectedIndex=i;
					return;
				}
			}
			
		}
	
	
	</script>
	
	
	
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<c:choose>
	<c:when test="${not empty employee}">
		<form method="POST" action="doRepEditPersonalInfo">
			<input type="hidden" name="SSN" value="<c:out value="${SSN}"/>">
			<input type="hidden" name="id" value="${id}"/>
 			<input type="hidden" name="startDate" value="${startDate}"/>
 			<input type="hidden" name="lastName" value="${lastName}"/>
 			<input type="hidden" name="firstName" value="${firstName}"/>
 			<input type="hidden" name="hourlyRate" value="${hourlyRate}"/>
 			<input type="hidden" id="currentState" value="${state}"/>
 			<div class="managerform">
 			<ul>
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
				  	<select id="state" name="state" >
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
		  		
		  		<%--Submit Button --%>
		  	  	<li>
		  	  		<input type="submit" value="Submit"/>
		  	  	</li>  
 			</ul>
 			</div>
		</form>
	</c:when>
	<c:otherwise>
		No employee found
	</c:otherwise>
	</c:choose>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>