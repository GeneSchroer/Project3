package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserAccount;
import utils.LoginUtils;
import utils.MyUtils;


@WebServlet(urlPatterns = {"/doLogin"})
public class DoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public DoLoginServlet(){
		super();
	}
	
	@Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String rememberMeStr = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMeStr);
		
		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;
		
		if (userName == null || password == null|| userName.length()==0 || password.length() == 0){
			hasError = true;
			errorString = "Required username and password!";
		}
		else{
			Connection conn = MyUtils.getStoredConnection(request);
			try{
				user = LoginUtils.findUser(conn, userName, password);
				if (user == null){
					hasError = true;
					errorString= "User Name or Password invalid!";
				}
				}
				catch(SQLException e){
					e.printStackTrace();
					hasError=true;
					errorString = e.getMessage();
				}
		}
		
		//If User Name and Password are not valid,
		//Go back to login page with an error message
			if (hasError){
						request.setAttribute("errorString", errorString);
						request.setAttribute("userName", userName);
						
						RequestDispatcher dispatcher 
						= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login/loginView.jsp");
						dispatcher.forward(request, response);
			}
			
			//otherwise, store user information in a session.
			else{
				HttpSession session = request.getSession();
				session.removeAttribute("loginedUser");
				MyUtils.storeLoginedUser(session, user);
				if(remember){
					MyUtils.storeUserCookie(response, user);
				}
				else{
				MyUtils.deleteUserCookie(response);
				}
				if(user.getUserType().equals("Manager"))
					response.sendRedirect(request.getContextPath() + "/managers");
				else if(user.getUserType().equals("Representative"))
					response.sendRedirect(request.getContextPath() + "/representatives");
				else if(user.getUserType().equals("Customer"))
					response.sendRedirect(request.getContextPath() + "/customers");
			}
	}
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
    
