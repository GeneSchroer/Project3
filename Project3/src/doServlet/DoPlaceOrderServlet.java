package doServlet;

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

import beans.Account;
import beans.Stock;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/orderList/doPlaceOrder"})
public class DoPlaceOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DoPlaceOrderServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		String stockSymbol = request.getParameter("stockSymbol");
		String orderType = request.getParameter("orderType");
		String priceType = request.getParameter("priceType");
		String numShares = request.getParameter("numShares");
		String pricePerShare = request.getParameter("pricePerShare");
		String percentage = request.getParameter("percentage");
		Integer numSharesParsed=null;
		Integer percentageParsed=null;
		Float pricePerShareParsed=null;
		int clientId = 222222222;
		Integer accountId=2; //HARDCODED
		Integer brokerId = 1; // HARDCODED
		String errorStrNumShares=null;
		String errorStrPercentage=null;
		String errorStrOrderType=null;
		String errorStrPricePerShare=null;
		boolean hasError=false;
		
		
		//Order Type
		// if Order is Buy but Price is Hidden or Trailing Stop, throw an error
		if(orderType.equals("Buy") && (priceType.equals("Trailing Stop")||priceType.equals("Hidden Stop"))){
			hasError=true;
			errorStrOrderType="Error: Cannot buy with a trailing stop or hidden stop!";
		}
		
		//NumShares
		//Throw error if user input cannot be parsed
		try{
			numSharesParsed = Integer.parseInt(numShares);
		}catch(Exception e){
				hasError=true;
				errorStrNumShares="Error: Invalid # of shares!";
		}
		
		//PricePerShare
		//if price type is market or market-on-close and this text box is filled in, throw error
		if(!pricePerShare.equals("") && (priceType.equals("Market") 
									|| priceType.equals("Market On Close"))){
			hasError=true;
			errorStrPricePerShare="Error: PricePerShare only applies to hidden and trailing stops!";
		}
		//Throw error if Price Type is Hidden Stop but Price Per Share if empty
		else if(priceType.equals("Hidden Stop") && pricePerShare.equals("")){
			hasError=true;
			errorStrPricePerShare="Error: Price Per Share cannot be empty for a hidden stop";
		}
		else if((priceType.equals("Hidden Stop")||priceType.equals("Trailing Stop")) && !pricePerShare.equals("")){
			try{
				pricePerShareParsed = Float.parseFloat(pricePerShare);
			}catch(Exception e){
				hasError=true;
				errorStrPercentage="Error: Invalid Price Per Share!";
			}
		}
		else{
			pricePerShareParsed=(float) 0.0;
		}
		
			
		//Percentage
		//Throw error if user entered value but the order is not a trailing stop
		if(!percentage.equals("") && !priceType.equals("Trailing Stop")){
			hasError=true;
			errorStrPercentage="Error: Percentage only applies to trailing stops!";
		}
		else if(priceType.equals("Trailing Stop") && !percentage.equals("") && !pricePerShare.equals("")){
			hasError=true;
			errorStrPercentage="Error: Percentage and Price Per Share cannot both be filled";
		}
		else if	(priceType.equals("Trailing Stop") && !percentage.equals("")){
			//Throw error if number cannot be parsed
			try{
				percentageParsed=Integer.parseInt(percentage);
				//Throw error if number is not positive
				if(percentageParsed<=0){
					hasError=true;
					errorStrPercentage="Error: number must be positive";
				}
			}catch(Exception e){
				
					hasError=true;
					errorStrPercentage="Error: Invalid Percentage!";
			}
		}
		else{
			percentageParsed=0;
		}
		
		if(!hasError){
			try{
				CustomerUtils.placeOrder(conn, stockSymbol, orderType, priceType, 
				numSharesParsed, pricePerShareParsed,  percentageParsed, accountId, brokerId);
			}
			catch(SQLException e){
				hasError=true;
				e.printStackTrace();
				e.getMessage();
			}
		}
		if(hasError){
			request.setAttribute("errorStrNumShares", errorStrNumShares);
			request.setAttribute("errorStrPercentage", errorStrPercentage);
			request.setAttribute("errorStrPricePerShare", errorStrPricePerShare);
			request.setAttribute("errorStrOrderType", errorStrOrderType);
			
			request.setAttribute("numShares", numShares);
			request.setAttribute("percentage", percentage);
			request.setAttribute("pricePerShare", pricePerShare);
			try {
				List<Stock> stockList = CustomerUtils.getStockList(conn);
				List<Account> accountList = CustomerUtils.getAccountList(conn, clientId);
				request.setAttribute("stockList", stockList);
				request.setAttribute("accountList", accountList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/customers/placeOrderView.jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath() + "/customers/orderList");

		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
