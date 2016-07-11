package representatives;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Employee;
import beans.Location;
import utils.LoginUtils;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns={"/representatives/personalInfo"})
public class PersonalInfoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		int employeeId = LoginUtils.getId(session);
		Employee employee=null;//your information
		Location location=null;//your location
		try {
			//get your information and location
			employee = ManagerUtils.findEmployee(conn, employeeId);
			location = ManagerUtils.findLocation(conn, employee.getZipCode());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//pass values to page
		request.setAttribute("employee", employee);
		request.setAttribute("location", location);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/representatives/personalInfoView.jsp");
		dispatcher.forward(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
}
