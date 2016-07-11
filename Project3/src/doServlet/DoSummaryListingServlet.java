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

import beans.Stock;
import beans.SummaryListing;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns={"/managers/doSummaryListing"})
public class DoSummaryListingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public DoSummaryListingServlet(){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		//Get user input
		String stockSymbol = request.getParameter("stockSymbol");
		String type	= request.getParameter("stockType");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String stockSymbolCheckBox = request.getParameter("stockSymbolCheckBox");
		String stockTypeCheckBox = request.getParameter("stockTypeCheckBox");
		String customerNameCheckBox = request.getParameter("customerNameCheckBox");
		boolean hasListing=false;
		SummaryListing sumListing=null;
		List<Stock> stockList =null;
		List<String> stockTypeList = null;
		
		//If were searching based on stock symbol
		if(stockSymbolCheckBox!=null){
			// get summary listing
			try {
				sumListing = ManagerUtils.getSummaryListingByStockSymbol(conn, stockSymbol);
				//pass to page if listing was found
				if(sumListing!=null)
					request.setAttribute("stockSymbolListing", sumListing);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// if we're looking for summary listing of a stock type
		else if(stockTypeCheckBox!=null)
			try{
				//get summary listing
				sumListing = ManagerUtils.getSummaryListingByStockType(conn, type);
				//pass to page if listing was found
				if(sumListing!=null)
					request.setAttribute("stockTypeListing", sumListing);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//if searching by customer name
		else if(customerNameCheckBox!=null)
			try{
				//get summary listing
				sumListing = ManagerUtils.getSummaryListingByCustomerName(conn, firstName, lastName);
				// pass to page if listing was found
				if(sumListing!=null)
					request.setAttribute("customerNameListing", sumListing);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			//get stock and stock type list if user wants to check again
			stockList = ManagerUtils.getStockList(conn);
			stockTypeList =	ManagerUtils.getStockTypes(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hasListing=true;
		//pass values to page
		request.setAttribute("stockSymbolList", stockList);
		request.setAttribute("stockTypeList", stockTypeList);
		request.setAttribute("hasListing", hasListing);
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/managers/summaryListingView.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		doGet(request, response);
	}
}
