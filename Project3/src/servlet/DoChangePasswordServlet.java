package servlet;

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
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns={"/doChangePassword"})
public class DoChangePasswordServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DoChangePasswordServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String errorStrPassword=null;
		boolean hasError = false;
		UserAccount user = null;
		//error check
	
		if(password1.equals("")){
			hasError = true;
			errorStrPassword = "Error: Password cannot be null!";
		}
	
		if(!password1.equals(password2)){
			hasError = true;
			errorStrPassword = "Error: Passwords do not match!";
		}
	
		if(!hasError){
			try{
				HttpSession session = request.getSession();
				user = (UserAccount)session.getAttribute("loginedUser");
				CustomerUtils.changePassword(conn, user.getUserName(), password1);
			}catch(SQLException e){
				hasError = true;
				e.printStackTrace();
			}
		}
		if(hasError){
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/customers/changePasswordView.jsp");
				dispatcher.forward(request, response);
		}
		else{
			if(user.getUserType().equals("Manager"))
				response.sendRedirect(request.getContextPath()+"/managers");
			else if(user.getUserType().equals("Representative"))
				response.sendRedirect(request.getContextPath()+"/representatives");
			if(user.getUserType().equals("Customer"))
				response.sendRedirect(request.getContextPath()+"/customers");
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
