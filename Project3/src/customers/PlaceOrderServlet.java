package customers;

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

import beans.Account;
import beans.Stock;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/orderList/placeOrder"})
public class PlaceOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public PlaceOrderServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString=null;
		List<Stock> stockList=null;
		List<Account> accountList=null;
		try {
			stockList=CustomerUtils.getStockList(conn);
			
			//HARD-CODED!
			accountList=CustomerUtils.getAccountList(conn, 222222222);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("stockList", stockList);
		request.setAttribute("accountList", accountList);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/placeOrderView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
	
	
}
