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

import beans.OpenOrder;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/openOrderList"})
public class OpenOrderListServlet extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public OpenOrderListServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString = null;
		List<OpenOrder> list = null;
		try{
			list = RepresentativeUtils.getOpenOrderList(conn, 1); //HARD CODED
			
		}catch(SQLException e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
		// store the information before forwarding
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("openOrderList", list);
		
		//Forward to employeeListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/openOrderListView.jsp");
		dispatcher.forward(request, response);		 
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
