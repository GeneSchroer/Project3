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

import beans.Orders;
import beans.TrailingHistory;
import beans.Transaction;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns="/customers/trailingStopHistory")
public class TrailingStopHistoryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		int orderId = Integer.parseInt(request.getParameter("id")); // order row
		int transactionId=Integer.parseInt(request.getParameter("tId")); //transaction row

		List<TrailingHistory> list = null; //trailing history of a particular stock
		Transaction transaction = null;
		Orders order = null;
		try{
			//get trailing list history
			list = CustomerUtils.getTrailingHistory(conn, orderId); 
			// find transaction and order
			transaction=CustomerUtils.findTransaction(conn, transactionId);
			 order = CustomerUtils.findOrder(conn, orderId); 
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		//pass values to page
		request.setAttribute("trailingHistoryList", list);
		request.setAttribute("transaction", transaction);
		request.setAttribute("order", order);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/trailingStopHistoryView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
