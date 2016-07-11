package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Account;
import beans.Client;
import beans.HasStock;
import beans.Stock;
import utils.CustomerUtils;
import utils.LoginUtils;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/representatives/doRecordOrder"})
public class DoRecordOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DoRecordOrderServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		//get user inputs
		String stockSymbol = request.getParameter("stockSymbol");
		String orderType = request.getParameter("orderType");
		String priceType = request.getParameter("priceType");
		String numShares = request.getParameter("numShares");
		String pricePerShare = request.getParameter("pricePerShare");
		String percentage = request.getParameter("percentage");
		String pricePerShareCheckbox = request.getParameter("pricePerShareCheckbox");
		String percentageCheckbox = request.getParameter("percentageCheckbox");
		
		
		boolean hasError=false;
		Integer numSharesParsed=null;
		Float pricePerShareParsed=null;
		Double percentageParsed=null;
		Integer accountId=null; 
		Integer brokerId = null; 
		String errorStrDateTime=null;
		String errorStrNumShares=null;
		String errorStrPricePerShare=null;
		String errorStrPercentage=null;
		Timestamp dateTime = null;
		
		//BrokerId
		brokerId = LoginUtils.getId(session);

		//AccountId
		accountId = Integer.parseInt(request.getParameter("accountId"));
		
		
		//DateTime
		if (request.getParameter("now")!=null){
			dateTime = new Timestamp(System.currentTimeMillis());
		}
		else{
			try{
				dateTime = Timestamp.valueOf(request.getParameter("dateTime"));
				// Throw error if user date is after now
				if(dateTime.after(new Timestamp(System.currentTimeMillis()))){
					hasError=true;
					errorStrDateTime="Error: Date cannot be after now!";
				}
			}catch(Exception e){
				hasError=true;
				errorStrDateTime="Error: Invalid DateTime!";
			}
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
			
			}catch(Exception e){
				hasError=true;
				errorStrNumShares="Error: Invalid # of shares!";
		}
		
		//Throw an error if user attempts to sell more shares than he has in that account
		
		
		
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
								errorStrPercentage="Error: Percentage cannot be greater than 100!";	}
					
						}catch(Exception e){
							hasError=true;
							errorStrPercentage="Error: Invalid Percentage!";	}	}
				}
		// if everything worked out, record the order
		if(!hasError){
			try{
				
				RepresentativeUtils.recordOrder(conn, stockSymbol, orderType, priceType, dateTime, numSharesParsed, percentageParsed, pricePerShareParsed, accountId, brokerId );
			}
			catch(SQLException e){
				hasError=true;
				e.printStackTrace();
				e.getMessage();
			}
			
		}
		//if there was an error, stay on page and pass error messages
		if(hasError){
			boolean haveAccount=true;
			int clientId = Integer.parseInt(request.getParameter("clientId"));
			try {
				
				List<Client> clientList = RepresentativeUtils.getClientList(conn, brokerId);
				List<Account> accountList = RepresentativeUtils.getAccountList(conn, clientId);
				List<Stock> stockList = RepresentativeUtils.getStockList(conn);
				List<HasStock> hasStockList = CustomerUtils.getStockPortfolio(conn, clientId);
				request.setAttribute("clientId", clientId);
				request.setAttribute("clientList", clientList);
				request.setAttribute("accountList", accountList);
				request.setAttribute("stockList", stockList);
				request.setAttribute("hasStockList", hasStockList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				request.setAttribute("errorStrNumShares", errorStrNumShares);
				request.setAttribute("errorStrPricePerShare", errorStrPricePerShare);
				request.setAttribute("errorStrPercentage", errorStrPercentage);
				request.setAttribute("errorStrDateTime", errorStrDateTime);
				
				request.setAttribute("dateTime", dateTime);
				request.setAttribute("numShares", numShares);
				request.setAttribute("haveAccount", haveAccount);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/representatives/recordOrder.jsp");
		dispatcher.forward(request, response);
		}
		//otherwise go back to main menu
		else
			response.sendRedirect(request.getContextPath()+"/representatives");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
