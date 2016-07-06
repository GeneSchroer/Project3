<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeForms.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
  <meta charset="UTF-8">
<title>Edit Client</title>

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
<h3>Edit Client</h3>

<c:if test="${not empty client}">
	
	  	  
	
	<form method="POST" action="doEditClient">
		<input type="hidden" name="SSN" value="${SSN}"/>
	  	  <input type="hidden" name="id" value="${id}"/>
	  	  <input type="hidden" id="currentState" value=${state }/>
		<div class="managerform">
		<ul>
		<li>
			<span style="width: 200px;">
				Client Id: ${id}
			</span>
		</li>
		<li> 
			<span class="errordetails">
				${errorStrFirstName}
			</span> 
		  	<span>
			  	First name
			  	<span class="managertooltip">
				First name of client
			</span>
		  	</span>
		  	<input type="text" name="firstName" value="${firstName}"><br>
		</li>
		
		<li>
			<span class="errordetails">
				${errorStrLastName}
			</span>
			<span>
	  			Last name
	  			<span class="managertooltip">
	  			Last name of client
	  		</span>
	  		</span>
	  		<input type="text" name="lastName" value="${lastName}"><br>
  	  	</li>
  	 
  	 	<li>
	  	 	<span class="errordetails">
	  	 		${errorStrAddress}
	  	 	</span>
	  	  	
	  	  	<span>
	  	  		Address
	  	  		<span class="managertooltip">
			Client's address
		</span>
	  	  	</span>
			<input type="text" name="address" value="${address}"><br>
  		</li>
  	  	<!-- City -->
  	  	<li>
	  	  	<span class="errordetails">
	  	  		${errorStrCity}
	  	  	</span>
	  	  	<span>
	  	  		City
	  	  		<span class="managertooltip">
			Client's city of residence
		</span>
	  	  	</span>
	  	  	<input type="text" name="city" value="${city}"><br>
  	  	</li>

  	 <!-- State -->
  	<li>
	  	<span class="errordetails">
	  		${errorStrState}
	  	</span>
	  	<span>
	  		State
	  		<span class="managertooltip">
			Client's state of residence
		</span>
	  	</span>
	  	<select id="state" name="state">
			    <jsp:include page="_state.jsp"></jsp:include>
	  	</select>
  	</li>
  	<li>
  		<span class="errordetails">
  			${errorStrZipCode}
  		</span>
  		<span>
  			Zip Code
  			<span class="managertooltip">
			Client's zip code
		</span>
  		</span>
  		<input type="number" name="zipCode" min="1" max="99999"value="${zipCode}" required>
  	</li>
  	<li>
  		<span class="errordetails">
  			${errorStrTelephone}
  		</span>
 	 	<span>
 		 	Telephone
 		 	<span class="managertooltip">
			Client's telephone number
		</span>
 	 	</span>
		<input type="text" name= "telephone" min="1" max="9999999999" value="${telephone}" required><br>
  	</li>
  	
  	<li>
	  	<span class="errordetails">
	  		${errorStrEmail}
	  	</span>
	  	<span>  	
	  		Email
	  		<span class="managertooltip">
			Client's email address
		</span>
	  	</span>
	  	<input type="text" name="email" value="${email}" required>
  	</li>
  	
  	<li>
		<span class="errordetails">
	  		${errorStrRating}
		</span>
	  	<span>
	 		Rating
	 		<span class="managertooltip">
			Initial client rating
		</span>
	 	</span>
		<input type="number" name="rating" min="1" value="${rating}" required>
	</li>
	<li>
		<span class="errordetails">
			${errorStrCreditCardNumber}
		</span>
	  	<span style="width:120px; ">
	  	Credit Card # 
	  	<span class="managertooltip">
				Client's credit card number
			</span>	
	  	</span>
	  	<input type="text" name="creditCardNumber" value="${creditCardNumber}" required>
	</li>
	<li>
  	  <input type="submit" value="Submit"/>
	</li>
	</ul>
	</div>
	</form>
	</c:if>
	
	<a class="returnbtn" href="${pageContext.request.contextPath}/representatives/clientList">Return to Client List</a>

    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>