package managers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Stock;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/managers/editStock"})
public class EditStockServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public EditStockServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String stockSymbol = request.getParameter("stockSymbol");
		
		Stock stock = null;
		String errorString=null;
		boolean hasError = false;
		try{
			stock = ManagerUtils.findStock(conn, stockSymbol);
		}catch(SQLException e){
			e.printStackTrace();
			errorString=e.getMessage();
		}
		//Check if there was an error
		// or if the Employee does not exist
		// Redirect to employeeList page if that happens
		if(stock==null)
			hasError=true;
		if(errorString!=null && stock== null){
			response.sendRedirect(request.getServletPath() + "/managers/stockList");
			return;
		}
		if(!hasError){
		request.setAttribute("errorString", errorString);
		request.setAttribute("stockSymbol", stock.getStockSymbol());
		request.setAttribute("companyName", stock.getCompanyName());
		request.setAttribute("type", stock.getType());
		request.setAttribute("pricePerShare", stock.getPricePerShare());
		}
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/managers/editStockView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
