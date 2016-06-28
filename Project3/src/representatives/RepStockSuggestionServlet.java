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
import beans.UserAccount;
import utils.MyUtils;
import utils.RepresentativeUtils;
@WebServlet(urlPatterns = {"/representatives/stockSuggestionList"})
public class RepStockSuggestionServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		int brokerId = ((UserAccount)session.getAttribute("loginedUser")).getId();
		List<Client> clientList = null;
		try {
			clientList = RepresentativeUtils.getClientList(conn, brokerId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(clientList!=null && !clientList.isEmpty())
			request.setAttribute("clientList", clientList);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/representatives/stockSuggestionListView.jsp");
		dispatcher.forward(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
