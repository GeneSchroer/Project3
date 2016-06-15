package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.History;
import beans.Stock;
import utils.CustomerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns={"/customers/doStockHistory"})
public class DoStockHistoryServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn=MyUtils.getStoredConnection(request);
		String stockSymbol = request.getParameter("stockSymbol");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		Date fromDateParsed=null;	// date to start with
		Date toDateParsed = null;	// date to end with
		boolean hasError = false;
		String errorStrFromDate=null;
		String errorStrToDate=null;
		List<History> stockHistoryList=null;
		List<Stock> stockList=null;
		boolean hasStocks=false;
		//Check for errors
		
		
		//fromDate
		try{
			fromDateParsed = Date.valueOf(fromDate);
		}catch(Exception e){
			hasError=true;
			errorStrFromDate="Error: Invalid Date format!";
		}
		
		//toDate
		try{
			toDateParsed = Date.valueOf(toDate);
		}catch(Exception e){
			hasError=true;
			errorStrToDate="Error: Invalid Date format!";
		}
		
		
		if (toDateParsed.after( new Date(System.currentTimeMillis() ) )){
			hasError=true;
			errorStrToDate="Error: Cannot be after the current time!";
		}
		if(fromDateParsed.after(toDateParsed)){
			hasError=true;
			errorStrFromDate="Error: From Date cannot be after To Date![";
		}
		
		//if there was no error, try to get stock lists
		if(!hasError){
			try{
				
				stockHistoryList=CustomerUtils.getStockHistoryList(conn, stockSymbol, fromDateParsed, toDateParsed);
			}catch(SQLException e){
				hasError=true;
				e.printStackTrace();
				errorStrFromDate="something happened";
			}
		}
		//if there was an error, forward error messages
		if(hasError){
			request.setAttribute("errorStrFromDate", errorStrFromDate);
			request.setAttribute("errorStrToDate", errorStrToDate);
		}
		//otherwise, forward the stock history
		else{
			request.setAttribute("stockHistoryList", stockHistoryList);
		}
		try{
			stockList=CustomerUtils.getStockList(conn);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		if(stockHistoryList.isEmpty()){
			errorStrToDate="List is empty";
			request.setAttribute("errorStrToDate", errorStrToDate);
		}
		
		if(!stockList.isEmpty()){
			hasStocks=true;
			request.setAttribute("haveStocks", hasStocks);
		}
		request.setAttribute("stockList", stockList);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/customers/stockHistoryView.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
