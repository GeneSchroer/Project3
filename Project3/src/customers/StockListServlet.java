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

import beans.BestSeller;
import beans.Stock;
import utils.CustomerUtils;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet({"/customers/stockList"})
public class StockListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public StockListServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString=null;
		List<Stock> list=null; // list of stocks
		List<BestSeller> bestSellerList=null; // list of best selling stocks
		try{
			// get lists
			list = CustomerUtils.getStockList(conn);
			bestSellerList=CustomerUtils.getBestSellerList(conn);
		}catch(SQLException e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		//Store the information before forwarding
		request.setAttribute("errorString", errorString);
		if(list!=null && !list.isEmpty())
			request.setAttribute("stockList", list);
		if(bestSellerList!=null && !bestSellerList.isEmpty()){
			request.setAttribute("bestSellerList", bestSellerList);
		}
		
		//Forward to stockListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/stockListView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
