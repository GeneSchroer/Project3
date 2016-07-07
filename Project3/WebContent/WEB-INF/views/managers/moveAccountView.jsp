<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerStyle.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/managerForms.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Move Account</title>
</head>
<body>
	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>

	<div class="managerform">
		<form method="POST" action="doMoveAccount">
			<ul>
				<li>
					<span style="margin-bottom:5px;">
						Move From
						<span class="managertooltip">
							Move this client from the broker
						</span>
					</span>
					
					<select name="clientId">
						<c:forEach items="${clientList}" var="client">
							<option value="${client.id}">${client.firstName} ${client.lastName}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<span>
						Move To
						<span class="managertooltip">
							Move the client to this employee
						</span>
					</span>
					<select name="brokerId">
						<c:forEach items="${employeeList}" var="employee">
							<c:choose>
								<c:when test="${userId != employee.id && brokerId != employee.id }">
									<option value="${employee.id}">Id: ${employee.id} ${employee.firstName} ${employee.lastName}</option>						
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>	
				</li>			
				<li>
					<input type="submit" value="Submit"/>
				</li>
			</ul>		
		</form>
	</div>
	<a class="returnbtn" href = "${pageContext.request.contextPath}/managers/employeeList">Return to employee list</a>
	<a class="returnbtn" href = "${pageContext.request.contextPath}/managers">Return to main page</a>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
	

</html>