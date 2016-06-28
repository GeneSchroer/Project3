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
import javax.servlet.http.HttpSession;

import beans.Account;
import beans.FullOrder;
import beans.HasStock;
import beans.Stock;
import utils.CustomerUtils;
import utils.LoginUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/customers/doPlaceOrder"})
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
		String pricePerShareCheckbox = request.getParameter("pricePerShareCheckbox");
		String percentageCheckbox = request.getParameter("percentageCheckbox");
		Integer numSharesParsed=null;
		Double percentageParsed=null;
		Float pricePerShareParsed=null;
		Integer clientId = null;
		Integer accountId = null; 
		Integer brokerId = null; 
		String errorStrNumShares=null;
		String errorStrPercentage=null;
		String errorStrPricePerShare=null;
		boolean hasError=false;
		
		//Get Account Id
		accountId = Integer.parseInt(request.getParameter("accountId"));
		
		//Get Client Id
		HttpSession session = request.getSession();
		clientId = LoginUtils.getId(session);
		
		//Get Broker Id
		try {
			brokerId = CustomerUtils.getBrokerId(conn, clientId);
		} catch (SQLException e) {
			hasError=true;
			e.printStackTrace();
		}

		//NumShares
		//Throw error if user input cannot be parsed
		try{
			numSharesParsed = Integer.parseInt(numShares);
			//Throw error is input is zero or less
			if(numSharesParsed <= 0){
				hasError = true;
				errorStrNumShares="Error: Number of Shares must be positive!";
			}
		}catch(Exception e){
				hasError=true;
				errorStrNumShares="Error: Invalid # of shares!";
		}
		
		//Throw an error if user attempts to buy more shares than are available
		try{
			Stock stock = CustomerUtils.findStock(conn, stockSymbol);
			if(orderType.equals("Buy") && numSharesParsed > stock.getNumShares()){
				hasError=true;
				errorStrNumShares="Error: There are not enough shares available to buy!";
				}
		}catch(SQLException e){
			hasError=true;
			e.printStackTrace();	}
		
		//Throw an error if user attempts to sell more shares than he has in that account
		
		try{
			int userStocks = CustomerUtils.getSharesInAccount(conn, accountId, stockSymbol);
			if(orderType.equals("Sell") && numSharesParsed>userStocks){
				hasError=true;
				errorStrNumShares = "Error: Cannot sell more shares than you have!";
			}
		}catch(SQLException e){
			hasError=true;
			e.printStackTrace();
		}
		
		
		//Hidden Stop
		//PricePerShare
		//Throw error if input cannot be parsed
		if(priceType.equals("Hidden Stop")){
			try{
				pricePerShareParsed = Float.parseFloat(pricePerShare);
				Stock stock = CustomerUtils.findStock(conn, stockSymbol);
				// Throw error if input is zero or less
				if(pricePerShareParsed <= 0){
					hasError=true;
					errorStrPricePerShare = "Error: Price Per Share must be a positive number!";	}
				// Throw error if input is greater than current share price
				else if(pricePerShareParsed > stock.getPricePerShare()){
					hasError = true;
					errorStrPricePerShare = "Error: Stop Price cannot be greater than current market price";	}
			
			}catch(Exception e){
				hasError=true;
				errorStrPricePerShare = "Error: Not a valid number!";	}	}
		else if(priceType.equals("Trailing Stop")){
			//Throw error if user provided no input
			if(pricePerShareCheckbox==null && percentageCheckbox==null){
				hasError=true;
				errorStrPercentage="Error: Must choose either percentage or price per share!";	}
			
			else if(pricePerShareCheckbox!=null){
				// throw error if input cannot be parsed
				try{
					pricePerShareParsed = Float.parseFloat(pricePerShare);
					Stock stock = CustomerUtils.findStock(conn, stockSymbol);
					// throw error if input is zero or less
					if(pricePerShareParsed <= 0){
						hasError=true;
						errorStrPricePerShare = "Error: Price Per Share must be a positive number!";	}
					// throw error if input is greater than current share price
					else if(pricePerShareParsed > stock.getPricePerShare()){
						hasError = true;
						errorStrPricePerShare = "Error: Stop Price cannot be greater than current market price";	}
				
				}catch(Exception e){
					hasError=true;
					errorStrPricePerShare = "Error: Not a valid number!";	}	}	
			
			else if(percentageCheckbox!=null){
				// throw error if input cannot be parsed
				try{
					percentageParsed = Double.parseDouble(percentage);
					// throw error if input is zero or less
					if(percentageParsed<=0){
						hasError=true;
						errorStrPercentage="Error: Percentage must be positive";	}
					// throw error if input is 100 or greater
					else if(percentageParsed >= 100){
						hasError=true;
						errorStrPercentage="Error: Percentage cannot be 100!";	}
			
				}catch(Exception e){
					hasError=true;
					errorStrPercentage="Error: Invalid Percentage!";	}	}
		}
		//Place the order if there has been no errors
		if(!hasError){
			try{
				System.out.println("Price Per Share" + pricePerShareParsed);
				System.out.println("Percentage" + percentageParsed);
				accountId = Integer.parseInt(request.getParameter("accountId"));
				
				FullOrder fullOrder = new FullOrder(numSharesParsed, pricePerShareParsed, 0, null, percentageParsed, priceType, orderType, 0, null, null, null, accountId, brokerId, stockSymbol);
				
				CustomerUtils.placeOrder(conn, fullOrder);
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
			
			request.setAttribute("numShares", numShares);
			request.setAttribute("percentage", percentage);
			request.setAttribute("pricePerShare", pricePerShare);
			try {
				List<Stock> stockList = CustomerUtils.getStockList(conn);
				List<Account> accountList = CustomerUtils.getAccountList(conn, clientId);
				List<HasStock> hasStockList = CustomerUtils.getStockPortfolio(conn, clientId);
				request.setAttribute("stockList", stockList);
				request.setAttribute("accountList", accountList);
				request.setAttribute("hasStockList", hasStockList);
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
