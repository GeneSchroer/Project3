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
import javax.servlet.http.HttpSession;

import beans.Client;
import utils.RepresentativeUtils;
import utils.LoginUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/representatives/clientList"})
public class ClientListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ClientListServlet(){
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		int brokerId = LoginUtils.getId(session); 
		String errorString = null;
		List<Client> list = null; //list of clients
		try{
			//get list of clients
			list = RepresentativeUtils.getClientList(conn, brokerId);
		}catch(SQLException e){
		e.printStackTrace();
			errorString = e.getMessage();
		}
		
		
		// store the information before forwarding
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("clientList", list);
		
		//Forward to clientListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/representatives/clientListView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
