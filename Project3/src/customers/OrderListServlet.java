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

import beans.FullOrder;
import beans.UserAccount;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/orderList"})
public class OrderListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public OrderListServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString=null;
		HttpSession session = request.getSession();
		UserAccount user = (UserAccount)session.getAttribute("loginedUser");
		
		int clientId=user.getId();
		List<FullOrder> fullList=null;
		try{
			fullList  = CustomerUtils.getFullOrderList2(conn, clientId);
		}catch(SQLException e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		
		//request.setAttribute("errorString", errorString);
		//request.setAttribute("orderList", list);
		request.setAttribute("fullOrderList", fullList);
		//Forward to customerOrderListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/orderListView.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
	
	
}
