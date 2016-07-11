package doServlet;

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

import beans.FullOrder;
import beans.UserAccount;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns={"/customers/recentOrders"})
public class DoRecentOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DoRecentOrderServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		String stockSymbol = request.getParameter("stockId"); //id of stock we want
		int clientId = ((UserAccount)session.getAttribute("loginedUser")).getId(); //id of client
		List<FullOrder> orderList = null;
	
		//get list of client's recent orders
		try{
			orderList = CustomerUtils.getRecentOrders(conn, clientId, stockSymbol);
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(orderList!=null && !orderList.isEmpty())
			request.setAttribute("orderList", orderList);
		request.setAttribute("stockSymbol", stockSymbol);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/customers/recentOrderView.jsp");
			dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
