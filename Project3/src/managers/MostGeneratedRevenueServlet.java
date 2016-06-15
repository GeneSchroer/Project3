package managers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.HighRoller;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/managers/mostGeneratedRevenue"})
public class MostGeneratedRevenueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public MostGeneratedRevenueServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		
		String errorString = null;
		HighRoller bestRepresentative = null;
		HighRoller bestCustomer = null;
		try{
			bestRepresentative = ManagerUtils.findRepresentativeWithMostRevenue(conn);
			bestCustomer = ManagerUtils.findCustomerWithMostRevenue(conn);
		}catch(SQLException e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("bestCustomer", bestCustomer);
		request.setAttribute("bestRepresentative", bestRepresentative);
		
		//Forward to employeeListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/managers/mostGeneratedRevenueView.jsp");
		dispatcher.forward(request, response);
		 
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
	
	
}
