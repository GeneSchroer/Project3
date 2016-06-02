package servlet;

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

import beans.Stock;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/recordOrder"})
public class RecordOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public RecordOrderServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString = null;
		List<Stock> list = null;
		try{
			list=RepresentativeUtils.getStockList(conn);
		}catch(SQLException e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		request.setAttribute("errorSting", errorString);
		request.setAttribute("stockList", list);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/recordOrder.jsp");
		dispatcher.forward(request, response);
		
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}