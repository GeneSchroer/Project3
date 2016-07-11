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

		String dateOpened=request.getParameter("dateOpened");
		String now = request.getParameter("now");
		String errorStrDateOpened=null;
		boolean hasError=false;
		Date dateOpenedParsed=null;

		
		//Date Opened
		
		/* If the checkbox was clicked */
		/* Then the account will be "created" right now*/
		if(now !=null){
			dateOpenedParsed = new Date(System.currentTimeMillis());
		}
		// Otherwise, it will be created at another time
		else{
			try{
				dateOpenedParsed= Date.valueOf(dateOpened);
			}catch(Exception e){
				hasError=true;
				errorStrDateOpened="Error: Invalid Date!";
			}
		}
		// if there is no error, then add the account
		if(!hasError){
			try{
				RepresentativeUtils.addAccount(conn, dateOpenedParsed, clientId);
			}catch(SQLException e){
				e.printStackTrace();
				hasError = true;
			}
			
		}
		// if there is still no error, return to the client list
		// otherwise stay on this page but return some error information
		if(hasError){
			request.setAttribute("clientId", clientId);
			request.setAttribute("dateOpened", dateOpened);
			request.setAttribute("errorStrDateOpened", errorStrDateOpened);
			
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/representatives/createAccountView.jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath() + "/representatives/accountList?id=" + clientId);

		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
