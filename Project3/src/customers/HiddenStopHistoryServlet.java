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
		List<History> list = null;
		try{
			list = CustomerUtils.getHiddenStopHistory(conn, stockId, orderId);
			transaction=CustomerUtils.findTransaction(conn, transactionId);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		request.setAttribute("transaction", transaction);
		request.setAttribute("hiddenHistoryList", list);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/hiddenStopHistoryView.jsp");
		dispatcher.forward(request, response);
	}
	private List<History> getHiddenStopHistory(Connection conn, String stockId, int orderId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
