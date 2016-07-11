package managers;

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

@WebServlet(urlPatterns={"/managers/mostActivelyTradedStocks"})
public class MostActivelyTradedStocksServlet extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	public MostActivelyTradedStocksServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn=MyUtils.getStoredConnection(request);
		List<Stock> mostActivelyTradedStocksList=null; // list of most actively traded stocks
		
		try{
			//get list of stocks
			mostActivelyTradedStocksList=ManagerUtils.getMostActivelyTradedStock(conn);
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		//pass list to the page
		if(!mostActivelyTradedStocksList.isEmpty())
			request.setAttribute("mostActivelyTradeStocksList", mostActivelyTradedStocksList);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/managers/mostActivelyTradedStocksView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
