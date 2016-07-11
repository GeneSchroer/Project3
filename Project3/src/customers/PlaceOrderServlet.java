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
import beans.BestSeller;
import beans.HasStock;
import beans.Stock;
import utils.CustomerUtils;
import utils.LoginUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/placeOrder"})
//Place an order for a stock
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
		List<Stock> stockList=null;		// need the list of stocks to buy/sell
		List<Account> accountList=null;	// need the user's accounts
		List<HasStock> hasStockList=null;	// user needs to know his own portfolio
		List<BestSeller> bestSellerList=null; // user may want to know best selling stocks
		try {
			//get all lists
			stockList=CustomerUtils.getStockList(conn);
			HttpSession session = request.getSession();
			int clientId =  LoginUtils.getId(session);
			accountList=CustomerUtils.getAccountList(conn, clientId);
			
			hasStockList=CustomerUtils.getStockPortfolio(conn, clientId);
			bestSellerList=CustomerUtils.getBestSellerList(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		//pass all values to page
		request.setAttribute("errorString", errorString);
		request.setAttribute("stockList", stockList);
		if(bestSellerList!=null && !bestSellerList.isEmpty())
			request.setAttribute("bestSellerList", bestSellerList);
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
