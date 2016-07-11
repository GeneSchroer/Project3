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
		//get user input
		String searchString = request.getParameter("search");
		//used to break up user string
		StringTokenizer tokenizer = new StringTokenizer(searchString);
		List<String> searchList = new ArrayList<String>(); //holds keywords
		List<Stock> stockList = null;
		//break up user string into list of tokens
		while(tokenizer.hasMoreTokens()){
			searchList.add(tokenizer.nextToken());
		}
		
		//get list based on user keywords
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
