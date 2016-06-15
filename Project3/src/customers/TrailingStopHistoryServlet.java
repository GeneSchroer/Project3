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

import beans.TrailingHistory;
import beans.Transaction;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns="/customers/trailingStopHistory")
public class TrailingStopHistoryServlet extends HttpServlet{
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		int orderId = Integer.parseInt(request.getParameter("id")); 
		int transactionId=Integer.parseInt(request.getParameter("tId"));
		List<TrailingHistory> list = null;
		Transaction transaction = null;
		try{
			list = CustomerUtils.getTrailingHistory(conn, orderId);
			transaction=CustomerUtils.findTransaction(conn, transactionId);
		}catch(SQLException e){
			e.printStackTrace();
		}
		request.setAttribute("trailingHistoryList", list);
		request.setAttribute("transaction", transaction);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/trailingStopHistoryView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
