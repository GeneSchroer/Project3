package representatives;

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
import beans.AccountDetails;
import beans.Client;
import beans.HasStock;
import beans.Stock;
import utils.CustomerUtils;
import utils.LoginUtils;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/representatives/recordOrder"})
public class RecordOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public RecordOrderServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		int clientId = Integer.parseInt(request.getParameter("clientId"));
		boolean haveAccount=false;
		int brokerId = LoginUtils.getId(session);
		List<Stock> stockList = null; //list of stocks
		List<HasStock> hasStockList= null;//list of client's stocks
		List<Client> clientList = null;//list of clients
		List<Account> accountList=null;// list of a certain client's accounts
		try{
			//get all lists
			stockList=RepresentativeUtils.getStockList(conn);
			hasStockList = CustomerUtils.getStockPortfolio(conn, clientId);
			clientList = RepresentativeUtils.getClientList(conn, brokerId);
			accountList = CustomerUtils.getAccountList(conn, clientId);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		//pass all values to page
		if(accountList!=null && !accountList.isEmpty()){
			haveAccount=true;
			request.setAttribute("accountList", accountList);	}
		
		if(hasStockList!=null && !hasStockList.isEmpty()){
			request.setAttribute("hasStockList", hasStockList);	}
	
		
		request.setAttribute("stockList", stockList);
		request.setAttribute("clientList", clientList);
		request.setAttribute("haveAccount", haveAccount);
		request.setAttribute("clientId", clientId);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/representatives/recordOrder.jsp");
		dispatcher.forward(request, response);
		
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
