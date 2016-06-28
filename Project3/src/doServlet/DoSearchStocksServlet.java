package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Stock;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns="/customers/doSearchStocks")
public class DoSearchStocksServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public DoSearchStocksServlet(){
		super();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String searchString = request.getParameter("search");
		StringTokenizer tokenizer = new StringTokenizer(searchString);
		List<String> searchList = new ArrayList<String>();
		List<Stock> stockList = null;
		while(tokenizer.hasMoreTokens()){
			searchList.add(tokenizer.nextToken());
		}
		
		try{
			stockList = CustomerUtils.searchStocks(conn, searchList);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		request.setAttribute("stockList", stockList);
		RequestDispatcher dispatcher = request.getServletContext()
			.getRequestDispatcher("/WEB-INF/views/customers/searchStocksView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
