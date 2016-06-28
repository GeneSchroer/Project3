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

@WebServlet(urlPatterns={"/managers/doUpdateStock"})
public class DoUpdateStockServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DoUpdateStockServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String stockSymbol = request.getParameter("stockSymbol");
		String companyName = request.getParameter("companyName");
		String type = request.getParameter("type");
		float pricePerShare=0;
		int numShares=0;
		boolean hasError=false;
		String errorStrPricePerShare=null;
		String errorStrNumShares=null;
		
		Stock stock=null;
		String errorString=null;
		
		//Error Checking
		//Price Per Share
		try{
			pricePerShare = Float.parseFloat(request.getParameter("pricePerShare"));
			if(pricePerShare<=0){
				hasError=true;
				errorStrPricePerShare="Error: Price must be positive!";
			}
		}catch(Exception  e){
			hasError = true;
			errorStrPricePerShare="Error: Invalid Share Price!";
		}
		//NumShares
		try{
			numShares= Integer.parseInt(request.getParameter("numShares"));
			if(numShares<0){
				hasError=true;
				errorStrNumShares="Error: Available shares cannot be negative!";
			}
		}catch(Exception  e){
			hasError = true;
			errorStrNumShares="Error: Invalid number!";
		}
		
		
		
		if(!hasError){
			stock = new Stock(stockSymbol, companyName, type, pricePerShare, numShares);
			System.out.println(stock.getPricePerShare());
			try{
				ManagerUtils.updateStock(conn, stock);
			}
			catch(SQLException e){
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		
		if(hasError){
			request.setAttribute("errorStrPricePerShare", errorStrPricePerShare);
			request.setAttribute("errorStrNumShares", errorStrNumShares);
			request.setAttribute("pricePerShare", pricePerShare);
			request.setAttribute("numShares", numShares);
			request.setAttribute("stockSymbol", stockSymbol);
			request.setAttribute("companyName", companyName);
			request.setAttribute("type", type);
			request.setAttribute("stock", stock);
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/managers/updateStockPriceView.jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath() + "/managers/stockList");
		}
	} 
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
