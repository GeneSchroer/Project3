<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginStyle.css">
    <meta charset="UTF-8">
    <title>Login</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Login Page</h3>
 
    <p style="color: red;">${errorString}</p>
    <form method="POST" action="doLogin">
    <div class="loginpage">
       <table>
          <tr>
             <td>
             	<span>
             		User Name
             	</span>
             </td>
             <td><input type="text" name="userName" value= "${userName}" required/> </td>
          </tr>
          <tr>
             <td>
             	<span>
             		Password
             	</span>
             </td>
             <td><input type="password" name="password" value= "${password}" required/> </td>
          </tr>
          <tr>
             <td colspan ="2">
                <input type="submit" value= "Submit" />
               
              	<a href="${pageContext.request.contextPath}/">Cancel</a>
              </td>
          </tr>
       </table>
       </div>
    </form>
 
    <p style="color:blue;">User Name/password: DWarren/password(manager)</p>
 
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>