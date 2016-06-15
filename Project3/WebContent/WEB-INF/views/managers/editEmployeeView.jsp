<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 <head>
  	<meta charset="UTF-8">
  	<title>Edit Employee</title>
 </head>
<body>
	<h3>Edit Employee</h3>

	<c:if test="${not empty employee}">
	  <form method="POST" action="doEditEmployee">
	  
	  <p style="color:red;">Employee Id: ${id}</p>
	  <p style="color:red;">SSN: ${SSN}</p>
	  <p style="color:red;">Start Date: ${startDate}</p>
	  
	  <p style="color: red;">${errorStrFirstName}</p> 
	  First name:<br>
	  <input type="text" name="firstName" value="${firstName}"><br>
	  
	  <p style="color: red;">${errorStrLastName}</p>
	  Last name:<br>
	  <input type="text" name="lastName" value="${lastName}"><br>
  	  
  	 
  	  <input type="hidden" name="SSN" value="${SSN}">
  	 
  	 
  	  <p style="color: red;">${errorStrAddress}</p>
  	  Address:<br>
  	  <input type="text" name="address"value="${address}"><br>
  
  	  
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
 
 	  <input type="hidden" name="startDate" value="${startDate}">
 
  	  <p style="color: red;">${errorStrHourlyRate}</p>
  	  Hourly Rate<br>  
	  <input type="number" name="hourlyRate" min="1" value="${hourlyRate}"><br>

  	  <input type="submit" value="Submit"/>
  	  <a href="${pageContext.request.contextPath}/managers/employeeList">Return to employee list</a>
	</form>
	</c:if>
</body>
</html>