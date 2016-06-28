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

import beans.History;
import beans.Orders;
import beans.Transaction;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns="/customers/hiddenStopHistory")
public class HiddenStopHistoryServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public HiddenStopHistoryServlet(){
		super();
	}@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		Connection conn = MyUtils.getStoredConnection(request);
		int orderId = Integer.parseInt(request.getParameter("id"));
		int transactionId=Integer.parseInt(request.getParameter("tId"));
		String stockId=request.getParameter("sId");
		Transaction transaction = null;
		Orders order = null;
		List<History> list = null;
		try{
			order = CustomerUtils.findOrder(conn, orderId);
			System.out.println(order.getDateTime());
			transaction=CustomerUtils.findTransaction(conn, transactionId);
			System.out.println(transaction.getDateTime());
			list = CustomerUtils.getHiddenStopHistory(conn, stockId, order.getDateTime(), transaction.getDateTime());
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		request.setAttribute("transaction", transaction);
		System.out.println(list.size());
		request.setAttribute("hiddenHistoryList", list);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/hiddenStopHistoryView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
