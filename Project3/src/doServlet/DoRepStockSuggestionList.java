package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;
import beans.Stock;
import beans.UserAccount;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/representatives/doStockSuggestionList"})
public class DoRepStockSuggestionList extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		int brokerId = ((UserAccount)session.getAttribute("loginedUser")).getId();
		int clientId = Integer.parseInt(request.getParameter("clientId"));
		List<Client> clientList = null; 
		List<Stock> stockList = null;
		Client targetClient = null;
		//attempt to get the stock suggestion list for a particular client
		try {
			stockList = RepresentativeUtils.getStockSuggestionList(conn, clientId);
			//these two are needed in case there's an error
			clientList = RepresentativeUtils.getClientList(conn, brokerId);
			targetClient = RepresentativeUtils.findClient(conn, clientId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//if the list isn't empty, pass it to page
		if(stockList!=null && !stockList.isEmpty())
			request.setAttribute("suggestedStockList", stockList);
		//otherwise, pass a parameter letting the page no there were no stocks
		else
			request.setAttribute("noStocks", "none");
		request.setAttribute("clientList", clientList);
		request.setAttribute("targetClient", targetClient);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/representatives/stockSuggestionListView.jsp");
		dispatcher.forward(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
