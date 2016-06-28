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

import beans.SalesReport;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/managers/doSalesReport"})
public class DoSalesReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public DoSalesReportServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		String errorString=null;
		String errorStrMonth=null;
		String errorStrYear=null;
		boolean hasError=false;
		boolean doSales=true;
		String regex=null;
		List<SalesReport> list = null;
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		try{
			Integer.parseInt(year);
		}catch(Exception e){
			hasError=true;
			errorStrYear="Error: Invalid year";
		}
		try{
			int monthParsed= Integer.parseInt(month);
			if(monthParsed<0 || monthParsed>12){
				hasError=true;
				errorStrMonth="Error: Invalid month!";
			}
		}catch(Exception e){
			hasError=true;
			errorStrMonth="Error: Invalid month!";
		}
		
		
		if(!hasError){
			try{
				list = ManagerUtils.getSalesReport(conn, year, month);
			}catch(SQLException e){
				hasError=true;
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("errorString", errorString);
		request.setAttribute("errorStrMonth", errorStrMonth);
		request.setAttribute("errorStrYear", errorStrYear);
		if(list != null && !list.isEmpty())
			request.setAttribute("salesReportList", list);
		//Forward to customerOrderListView.jsp
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/managers/salesReportView.jsp");
		request.setAttribute("doSales", doSales);
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
