<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Login</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Login Page</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <form method="POST" action="doLogin">
       <table border="0">
          <tr>
             <td>User Name</td>
             <td><input type="text" name="userName" value= "${userName}" /> </td>
          </tr>
          <tr>
             <td>Password</td>
             <td><input type="password" name="password" value= "${password}" /> </td>
          </tr>
          <tr>
             <td colspan ="2">
                <input type="submit" value= "Submit" />
                <a href="${pageContext.request.contextPath}/">Cancel</a>
             </td>
          </tr>
       </table>
    </form>
 
    <p style="color:blue;">User Name/password: WSmith/password(manager), DSmith/password (representative), VDu/password(customer) </p>
 
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>