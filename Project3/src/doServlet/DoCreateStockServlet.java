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

@WebServlet(urlPatterns = {"/managers/stockList/doCreateStock"})
public class DoCreateStockServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public DoCreateStockServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		String stockSymbol 		= request.getParameter("stockSymbol");
		String companyName 		= request.getParameter("companyName");
		String type 			= request.getParameter("type");
		String pricePerShare 	= request.getParameter("pricePerShare");
		String numShares		= request.getParameter("numShares");
		float pricePerShareParsed=0;
		int numSharesParsed=0;
		String errorStrStockSymbol, errorStrCompanyName, errorStrType, errorStrPricePerShare, errorStrNumShares;
		String regex=null;
		Stock stock = null;
		boolean hasError=false;
		//check for errors
		
		
		//Stock Symbol
		errorStrStockSymbol=null;
		regex="[A-Z]+";
		if(stockSymbol==null||!stockSymbol.matches(regex)){
			hasError=true;
			errorStrStockSymbol = "Error: Invalid Stock Symbol!";
		}
		
		
		//check if the stock symbol was taken
		Stock hasStock=null;
		try {
			 hasStock = ManagerUtils.findStock(conn, stockSymbol);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (hasStock != null){
			hasError=true;
			errorStrStockSymbol="Error: Stock already exists!";
		}
			
		
		//Company Name
		errorStrCompanyName=null;
		if(companyName==null){
			hasError=true;
			errorStrCompanyName = "Error: Company Name cannot be null!";
		}
		
		//Stock Type
		errorStrType=null;
		regex="[A-Z][a-z]+[\\s[A-Z][a-z]+]*";
		if(type==null){
			hasError=true;
			errorStrType = "Error: Stock Type cannot be null!";
		}
		if(!type.matches(regex)){
			
			hasError=true;
			errorStrType = "Error: Invalid Stock Type!";
		}
		
		//Price Per Share
		errorStrPricePerShare=null;
		try{
			pricePerShareParsed=Float.parseFloat(pricePerShare);
			if(pricePerShareParsed<=0){
				hasError=true;
				errorStrPricePerShare="Error: Price must be positive!";
			}
		}catch(Exception e){
			hasError=true;
			errorStrPricePerShare="Error: Not a valid price!";
		}
		
		//Price Per Share
		errorStrNumShares=null;
		try{
			numSharesParsed=Integer.parseInt(numShares);
			if(pricePerShareParsed < 0){
				hasError=true;
				errorStrNumShares="Error: Shares available cannot be negative!";
			}
		}catch(Exception e){
			hasError=true;
			errorStrNumShares="Error: Not a valid amount!";
		}
		
		if(!hasError){
			stock = new Stock(stockSymbol, companyName, type, pricePerShareParsed, numSharesParsed);
			try{
				ManagerUtils.addStock(conn, stock);
			}catch(SQLException e){
				e.printStackTrace();
				String errorString = e.getMessage();
			}
		}
		if(hasError){
			request.setAttribute("errorStrStockSymbol", errorStrStockSymbol);
			request.setAttribute("errorStrCompanyName", errorStrCompanyName);
			request.setAttribute("errorStrType", errorStrType);
			request.setAttribute("errorStrPricePerShare", errorStrPricePerShare);
			request.setAttribute("errorStrNumShares", errorStrNumShares);
			request.setAttribute("stockSymbol", stockSymbol);
			request.setAttribute("companyName", companyName);
			request.setAttribute("type", type);
			request.setAttribute("pricePerShare", pricePerShare);
			request.setAttribute("numShares", numShares);
			RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/managers/createStockView.jsp");
			dispatcher.forward(request, response);
		}			
		//If everything worked, redirect to the employeeList
		else
			response.sendRedirect(request.getContextPath() + "/managers/stockList");
			
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
