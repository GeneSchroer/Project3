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

import utils.ManagerUtils;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns="/managers/doDeleteEmployee")
public class DoDeleteEmployeeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		//Get the employee's Id and SSN
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int SSN = Integer.parseInt(request.getParameter("SSN"));
		boolean hasDeleted=false; // boolean used to check if person was deleted
		try{
			hasDeleted = ManagerUtils.deleteEmployee(conn, employeeId, SSN);
		}catch(SQLException e){
			e.printStackTrace();
		}
		// if person was deleted, return to employeelist
		if(hasDeleted)
			response.sendRedirect(request.getContextPath()+"/managers/employeeList");
		//otherwise stay on page
		else{
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/managers/deleteEmployeeView.jsp");
			dispatcher.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
}
