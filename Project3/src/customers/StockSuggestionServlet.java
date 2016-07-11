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

import beans.Stock;
import beans.UserAccount;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/suggestedStock"})
public class StockSuggestionServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		UserAccount user = (UserAccount)session.getAttribute("loginedUser");
		int clientId= user.getId();
		List<Stock> list = null; //list of suggested stocks
		try{
			//get stock list
			list=CustomerUtils.getStockSuggestionList(conn, clientId);
		}catch(SQLException e){
			e.printStackTrace();
		}
		//pass stock list to page
		request.setAttribute("stockList", list);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/customers/stockSuggestionView.jsp");
		dispatcher.forward(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
