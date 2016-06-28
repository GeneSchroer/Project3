<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
  <meta charset="UTF-8">
<title>Edit Client</title>

</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
<h3>Edit Client</h3>

<c:if test="${not empty client}">
	<form method="POST" action="doEditClient">
	  
	  <p style="color:red;">Client Id: ${id}</p>
	  
	  <p style="color: red;">${errorStrFirstName}</p> 
	  First name:<br>
	  <input type="text" name="firstName" value="${firstName}"><br>
	  
	  <p style="color: red;">${errorStrLastName}</p>
	  Last name:<br>
	  <input type="text" name="lastName" value="${lastName}"><br>
  	  
  	 
  	  <input type="hidden" name="SSN" value="${SSN}">
  	 
  	 
  	  <p style="color: red;">${errorStrAddress}</p>
  	  Address:<br>
  	  <input type="text" name="address" value="${address}"><br>
  
  	  <!-- City -->
  	  <p style="color: red;">${errorStrCity}</p>
  	  City:<br>
  	  <input type="text" name="city" value="${city}"><br>
  	  
  	  <!-- State -->
  	  <p style="color: red;">${errorStrState}</p>
  	  State:<br>
  	  <input type="text" name="state" value="${state}"><br>
  	  
  	  <p style="color: red;">${errorStrZipCode}</p>
  	  Zip Code:<br>
  	  <input type="number" name="zipCode" min="1" max="99999"value="${zipCode}"><br>
  
  	  <p style="color: red;">${errorStrTelephone}</p>
  	  Telephone:<br>
  	  <input type="text" name= "telephone" min="1" max="9999999999" value="${telephone}"><br>
  
  
  
  	  <input type="hidden" name="id" value="${id}">
 
  	  <p style="color: red;">${errorStrEmail}</p>
  	  Email:<br>  
	  <input type="text" name="email" value="${email}"><br>

	  <p style="color: red;">${errorStrRating}</p>
  	  Rating:<br>  
	  <input type="number" name="rating" value="${rating}"><br>
	  
	  <p style="color: red;">${errorStrCreditCardNumber}</p>
  	  Credit Card Number:<br>  
	  <input type="text" name="creditCardNumber" value="${creditCardNumber}"><br>
	  

  	  <input type="submit" value="Submit"/>
  	  <a href="${pageContext.request.contextPath}/representatives/clientList">Return to Client List</a>
	</form>
	</c:if>

    <jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>