package customers;

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

import beans.Client;
import beans.Location;
import utils.LoginUtils;
import utils.ManagerUtils;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns={"/customers/personalInfo"})
public class PersonalInfoServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		int clientId = LoginUtils.getId(session);
		Client client=null;
		Location location=null;
		try {
			client = RepresentativeUtils.findClient(conn, clientId);
			location = ManagerUtils.findLocation(conn, client.getZipCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("client", client);
		request.setAttribute("location", location);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/customers/personalInfoView.jsp");
		dispatcher.forward(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
}
