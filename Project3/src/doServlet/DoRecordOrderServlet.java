package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/doRecordOrder"})
public class DoRecordOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DoRecordOrderServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		String stockSymbol = request.getParameter("stockSymbol");
		String orderType = request.getParameter("orderType");
		String priceType = request.getParameter("priceType");
		String numShares = request.getParameter("numShares");
		String percentage = request.getParameter("percentage");
		
		Integer numSharesParsed=null;
		Integer percentageParsed=null;
		Integer accountId=2; //HARDCODED
		Integer brokerId = 1; // HARDCODED
		String errorStrNumShares=null;
		String errorStrPercentage=null;
		Timestamp dateTime = null;
		

		
		if (request.getParameter("now")!=null){
			dateTime = new Timestamp(System.currentTimeMillis());
			System.out.println("not null");
		}
		else{
			dateTime = Timestamp.valueOf(request.getParameter("dateTime"));
		}
		
		
		boolean hasError=false;
		try{
			numSharesParsed = Integer.parseInt(numShares);
		}catch(Exception e){
			hasError=true;
			errorStrNumShares="Error: Invalid # of shares!";
		}
		System.out.println(hasError);
		try{
			percentageParsed=Integer.parseInt(percentage);
		}catch(Exception e){
			hasError=true;
			errorStrPercentage="Error:Invalid Percentage";
		}
		if(!hasError){
			try{
				
				RepresentativeUtils.recordOrder(conn, stockSymbol, orderType, priceType, dateTime, numSharesParsed, percentageParsed, accountId, brokerId );
			}
			catch(SQLException e){
				e.printStackTrace();
				e.getMessage();
			}
			
		}
		if(hasError){
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INT/views/recordOrder.jsp");
		dispatcher.forward(request, response);
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
