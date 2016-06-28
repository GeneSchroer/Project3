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
import javax.servlet.http.HttpSession;

import beans.Account;
import beans.HasStock;
import beans.Stock;
import utils.CustomerUtils;
import utils.LoginUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/placeOrder"})
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
		List<HasStock> hasStockList=null;
		try {
			stockList=CustomerUtils.getStockList(conn);
			HttpSession session = request.getSession();
			int clientId =  LoginUtils.getId(session);
			accountList=CustomerUtils.getAccountList(conn, clientId);
			hasStockList=CustomerUtils.getStockPortfolio(conn, clientId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("stockList", stockList);
		if(accountList!=null && !accountList.isEmpty())
			request.setAttribute("accountList", accountList);
		if(hasStockList!=null && !hasStockList.isEmpty())
			request.setAttribute("hasStockList", hasStockList);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/placeOrderView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
	
	
}
