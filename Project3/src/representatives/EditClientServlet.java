package representatives;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Client;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns ={"/editClient"})
public class EditClientServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public EditClientServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
	
		// connect to the database
		Connection conn = MyUtils.getStoredConnection(request);
		
		//find the employee with this id
		String s= (String)request.getParameter("id");
		
		int id = Integer.parseInt(s);
		
		Client client= null;
		String errorString = null;
		try{
			client = RepresentativeUtils.findClient(conn, id);
		}catch(SQLException e){
			e.printStackTrace();
			errorString=e.getMessage();
		}
		//Check if there was an error
		// or if the Employee does not exist
		// Redirect to employeeList page if that happens
		if(errorString!=null && client == null){
			response.sendRedirect(request.getServletPath() + "/clientList");
			return;
		}
		
		// Store errorString in request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		
		request.setAttribute("client", client);
		request.setAttribute("id", client.getId());
		request.setAttribute("firstName", client.getFirstName());
		request.setAttribute("lastName", client.getLastName());
		request.setAttribute("address", client.getAddress());
		request.setAttribute("zipCode", client.getZipCode());
		request.setAttribute("telephone", client.getTelephone());

		request.setAttribute("email", client.getEmail());

		request.setAttribute("rating", client.getRating());

		request.setAttribute("creditCardNumber", client.getCreditCardNumber());
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editClientView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
