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

import beans.ClientInfo;
import beans.MailingList;
import beans.UserAccount;
import utils.RepresentativeUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/representatives/mailingList"})
public class MailingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public MailingListServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		int brokerId = ((UserAccount)session.getAttribute("loginedUser")).getId();
		String errorString = null;
		List<ClientInfo> list = null;
		try{
			list = RepresentativeUtils.getMailingList(conn, brokerId);
		}catch(SQLException e){
		e.printStackTrace();
			errorString = e.getMessage();
		}
		
		
		// store the information before forwarding
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("mailingListList", list);
		
		//Forward to employeeListView.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/representatives/mailingListView.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
