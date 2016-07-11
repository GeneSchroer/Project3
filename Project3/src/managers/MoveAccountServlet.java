package managers;

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
import javax.servlet.http.HttpSession;

import beans.Client;
import beans.Employee;
import utils.LoginUtils;
import utils.ManagerUtils;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns={"/managers/moveAccount"})
public class MoveAccountServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session  =  request.getSession();
		int brokerId = Integer.parseInt(request.getParameter("id"));
		int userId=LoginUtils.getId(session);
		boolean hasError=false;
		List<Client> clientList = null;//list of clients a certain employee has
		List<Employee> employeeList = null; //list of employees
		
		
		try {
			//get lists of people
			clientList = RepresentativeUtils.getClientList(conn, brokerId);
			employeeList = ManagerUtils.getEmployeeList(conn);
		} catch (SQLException e) {
			hasError=true;
			e.printStackTrace();
		}
		//pass all values to the page
		request.setAttribute("userId", userId);
		request.setAttribute("brokerId", brokerId);
		request.setAttribute("clientList", clientList);
		request.setAttribute("employeeList", employeeList);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/managers/moveAccountView.jsp");
		dispatcher.forward(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
		
}
