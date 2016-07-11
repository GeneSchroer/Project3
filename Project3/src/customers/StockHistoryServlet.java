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

import beans.Stock;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/stockHistory"})
public class StockHistoryServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn=MyUtils.getStoredConnection(request);
		boolean haveStocks=false;
		List<Stock> list=null; // list of stocks
		try{
			//get list of stocks
			list = CustomerUtils.getStockList(conn);
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(!list.isEmpty()){
			//pass haveStocks if we, well, have stocks
			haveStocks=true;
			request.setAttribute("haveStocks", haveStocks);
		}
		//pass stock list to page
		request.setAttribute("stockList", list);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/stockHistoryView.jsp");
		dispatcher.forward(request, response);
	
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
