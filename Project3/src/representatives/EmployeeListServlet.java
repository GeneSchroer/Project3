package representatives;

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

import beans.Employee;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/representatives/employeeList"})
public class EmployeeListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public EmployeeListServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		
		String errorString = null;
		List<Employee> list = null;//list of employees
		try{
			//get list of employees
			list = ManagerUtils.getEmployeeList(conn);
		}catch(SQLException e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		
		// store the information before forwarding
		
		request.setAttribute("errorString", errorString);
		if(list!=null&&!list.isEmpty())
			request.setAttribute("employeeList", list);
		
		//Forward to employeeListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/representatives/employeeListView.jsp");
		dispatcher.forward(request, response);
		 
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
	
}


