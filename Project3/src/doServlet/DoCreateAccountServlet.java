package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Account;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns={"/representatives/doCreateAccount"})
public class DoCreateAccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public DoCreateAccountServlet(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		int clientId = Integer.parseInt(request.getParameter("clientId") );
		String id = request.getParameter("id"); 
		String dateOpened=request.getParameter("dateOpened");
		String now = request.getParameter("now");
		String errorStrId=null;
		String errorStrDateOpened;
		Integer idParsed=null;
		boolean hasError=false;
		//error check
		Date dateOpenedParsed=null;
		//id
		try{
			idParsed = Integer.parseInt(id);
		}catch(Exception e){
			hasError=true;
			errorStrId="Error: Invalid Account Id!";
		}
		
		//Date Opened
		if(!dateOpened.equals("") && now!=null){
			hasError=true;
			errorStrDateOpened = "Error: Must choose a date!";		
		}
		else if(now !=null){
			dateOpenedParsed = new Date(System.currentTimeMillis());
		}
		else{
			try{
				dateOpenedParsed= Date.valueOf(dateOpened);
			}catch(Exception e){
				hasError=true;
				errorStrDateOpened="Error: Invalid Date!";
			}
		}
		
		if(!hasError){
			Account account = new Account(idParsed, dateOpenedParsed, clientId);
			try{
				RepresentativeUtils.addAccount(conn, account, clientId);
			}catch(SQLException e){
				e.printStackTrace();
				hasError = true;
			}
			
		}
		if(hasError){
			request.setAttribute("clientId", clientId);
			request.setAttribute("id", id);
			request.setAttribute("dateOpened", dateOpened);
			
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/representatives/createAccountView.jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath() + "/representatives/accountList");

		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
