package Managers;

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
import utils.ManagerUtils;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/summaryListing"})
public class SummaryListingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public SummaryListingServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString=null;
		List<Stock> stockList = null;
		List<String> stockTypeList = null;
		try{
			stockList = RepresentativeUtils.getStockList(conn);
			stockTypeList = ManagerUtils.getStockTypes(conn);
		}catch(SQLException e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("stockList", stockList);
		request.setAttribute("stockTypeList", stockTypeList);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/summaryListingView.jsp");
		
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
