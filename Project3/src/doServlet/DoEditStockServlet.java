package doServlet;

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

@WebServlet(urlPatterns = {"/managers/stockList/doEditStock"})
public class DoEditStockServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		String stockSymbol=request.getParameter("stockSymbol");
		String companyName=request.getParameter("companyName");
		String type = request.getParameter("type");
		float pricePerShare = Float.parseFloat(request.getParameter("pricePerShare"));
		String errorStrCompanyName, errorStrType;
		boolean hasError=false;
		Stock stock=null;
		//error check
		
		//Company Name
		errorStrCompanyName=null;
		if(companyName==null){
			hasError=true;
			errorStrCompanyName="Error: Company Name cannot be null!";
		}
		
		//Stock Type
		errorStrType=null;
		if(type==null){
			hasError=true;
			errorStrType="Error: Stock Type cannot be null!";
		}
		if(!hasError){
			stock=new Stock(stockSymbol, companyName, type, pricePerShare);
			try{
				ManagerUtils.updateStock(conn, stock);
			}catch(SQLException e){
				e.printStackTrace();
				String errorString = e.getMessage();
			}
		}
		if(hasError){
			request.setAttribute("stockSymbol", stockSymbol);
			request.setAttribute("companyName", companyName);
			request.setAttribute("type", type);
			request.setAttribute("pricePerShare", pricePerShare);
			
			request.setAttribute("errorStrCompanyName", companyName);
			request.setAttribute("errorStrType", errorStrType);
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/managers/editStockView.jsp");
			dispatcher.forward(request, response);
		}
		//If everything worked, redirect to the employeeList
		else{
			response.sendRedirect(request.getContextPath() + "/managers/stockList");
		}
		
	}
	private static final long serialVersionUID=1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
