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

@WebServlet(urlPatterns={"/representatives/editPersonalInfo"})
public class EditPersonalInfoView extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	Connection conn = MyUtils.getStoredConnection(request);
	HttpSession session = request.getSession();
	int employeeId = LoginUtils.getId(session);
	Employee employee=null;
	Location location=null;
	try {
		employee = ManagerUtils.findEmployee(conn, employeeId);
		location = ManagerUtils.findLocation(conn, employee.getZipCode());
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	request.setAttribute("id", employee.getId());
	request.setAttribute("SSN", employee.getSSN());
	request.setAttribute("startDate", employee.getStartDate());
	request.setAttribute("firstName", employee.getFirstName());
	request.setAttribute("lastName", employee.getLastName());
	request.setAttribute("address", employee.getAddress());
	request.setAttribute("city", location.getCity());
	request.setAttribute("state", location.getState());
	request.setAttribute("zipCode", employee.getZipCode());
	request.setAttribute("telephone", employee.getTelephone());
	request.setAttribute("hourlyRate", employee.getHourlyRate());
	request.setAttribute("employee", employee);
	request.setAttribute("location", location);
	RequestDispatcher dispatcher = request.getServletContext()
			.getRequestDispatcher("/WEB-INF/views/representatives/editPersonalInfoView.jsp");
	dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
}
