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

import beans.HasStock;
import beans.Portfolio;
import utils.CustomerUtils;
import utils.LoginUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/customers/stockHolding"})
public class StockHoldingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public StockHoldingServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
//		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		int clientId= LoginUtils.getId(session);
		String errorString = null;
		List<HasStock> list = null;
		try{
			list = CustomerUtils.getStockPortfolio(conn, clientId);
		}catch(SQLException e){
		e.printStackTrace();
			errorString = e.getMessage();
		}
		
		
		// store the information before forwarding
		
		request.setAttribute("errorString", errorString);
		if(list!=null && !list.isEmpty())
			request.setAttribute("stockHoldingList", list);
		
		//Forward to employeeListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/stockHoldingView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
