<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeForms.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/representativeStyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Client</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
	<h3>Create Client View</h3>
	

<p style="color: red;">${errorString}</p>
<form method="POST" action="doCreateClient">
  <div class="managerform">
  	<ul>
  
  	<%-- First Name --%>
  	<li>
	  	<span class="errordetails">
	  		${errorStrFirstName}
	  	</span>
	  	<span>
	  		First Name
	  		<span class="managertooltip">
				First name of client
			</span>
	  	</span>
	  	<input type="text" name="firstName" value="${firstName}" required>
  	</li>
  
  	<%-- Last Name --%>
  	<li>
  	
	  	<span class="errordetails">
	  		${errorStrLastName}
	  	</span>
	  	<span>
	  		Last Name
	  		<span class="managertooltip">
	  			Last name of client
	  		</span>
	  	</span>
  		<input type="text" name="lastName" value="${lastName}" required>
  	</li>
  	
  	<%-- User Name --%>
  	<li>
  		<span class="errordetails">
  			${errorStrUserName}
  		</span>
  		<span>
	 		User Name
	 		<span class="managertooltip">
				User name of client
			</span>
		</span>
  		<input type="text" name="userName" value="${userName}" required>
	</li>  
  
  	<%-- Password --%>
  	<li>
  		<span class="errordetails">
  			${errorStrPassword}
  		</span>
  		<span>
  			Password
  			<span class="managertooltip">
				A temporary password for a client
			</span>
  		</span>
	  	<input type="text" name="password" value="${password}" required>
	</li>

  	<%-- ClientId/SSN --%>  
  	<li>
	  	<span class="errordetails">
	  		${errorStrId}
	  	</span>
	  	<span>
	  		Client SSN
	  		<span class="managertooltip">
				Client's social security number
			</span>
	  	</span>
	  	<input type="number" name="id" min="1" max="999999999" value="${id}" required>
  	</li>
  	
  	<%-- Address --%>
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
	  <input type="text" name="address"value="${address}" required>
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
  		<input type="text" name="city"value="${city}" required>
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
	  	<select name="state">
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
 	<a class="returnbtn" href = "${pageContext.request.contextPath}/representatives/clientList">Return to Client page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>