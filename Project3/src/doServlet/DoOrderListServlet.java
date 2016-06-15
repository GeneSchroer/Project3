package doServlet;

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

import beans.FullOrder;
import beans.Stock;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/managers/doOrderList"})
public class DoOrderListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DoOrderListServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString=null;
		String errorStrLastName=null;
		String errorStrFirstName=null;
		boolean hasError=false;
		List<Stock> stockList = null;
		List<FullOrder> list = null;
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String stockSymbol = request.getParameter("stockSymbol");
		
		System.out.println(firstName);
		//Error checking

		
		//1) Check if the last or first name include invalid characters
		//(no point in checking for only a partial match in case the user messed up)
		String regex="[a-zA-Z]*";
		if(!firstName.matches(regex)){
			hasError=true;
			errorStrFirstName="Error: Invalid name!";
		}
		
		if(!lastName.matches(regex)){
			hasError=true;
			errorStrLastName="Error: Invalid name!";
		}
		
		//Set values to null if the user made no choices
		if (stockSymbol.equals("null"))
			stockSymbol=null;
		if(lastName.equals(""))
			lastName=null;
		if(firstName.equals(""))
			firstName=null;
		
		if(stockSymbol==null && lastName==null && firstName==null){
			hasError=true;
			errorString = "Error: Must include an option!";
		}
		if(!hasError){
			try {
				list = ManagerUtils.getOrderList(conn, lastName, firstName, stockSymbol);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			 stockList = ManagerUtils.getStockList(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		request.setAttribute("errorString", errorString);
		request.setAttribute("errorStrLastName", errorStrLastName);
		request.setAttribute("errorStrFirstName", errorStrFirstName);
		request.setAttribute("orderList", list);
		request.setAttribute("stockList", stockList);
		//Forward to customerOrderListView.jsp
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/managers/orderListView.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
