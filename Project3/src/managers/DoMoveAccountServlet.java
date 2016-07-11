package managers;

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

@WebServlet(urlPatterns={"/managers/doMoveAccount"})
public class DoMoveAccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		int clientId = Integer.parseInt(request.getParameter("clientId"));
		int brokerId = Integer.parseInt(request.getParameter("brokerId"));
		boolean moved = false;
		try {
			//check to see if employee's client was successfully moved
			moved= ManagerUtils.moveAccount(conn, clientId, brokerId);
		} catch (SQLException e) {
			moved=false;
			e.printStackTrace();
		}
		
		//forward values to page
		if(moved)
			response.sendRedirect(request.getContextPath()+"/managers/employeeList");
		else{
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/managers/moveAccountView.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
