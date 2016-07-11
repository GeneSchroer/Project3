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

import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns="/representatives/doDeleteClient")
public class DoDeleteClientServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DoDeleteClientServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		int clientId = Integer.parseInt(request.getParameter("clientId"));
		boolean hasDeleted=false; // boolean to check if person was deleted
		try{
			//delete the client, which also backsup database
			hasDeleted = RepresentativeUtils.deleteClient(conn, clientId);
		}catch(SQLException e){
			e.printStackTrace();
		}
		//if everything went well, return to client list
		if(hasDeleted)
			response.sendRedirect(request.getContextPath() + "/representatives/clientList");
		//otherwise stay on the page
		else{
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/representatives/deleteClientView.jsp");
			dispatcher.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
	
}
