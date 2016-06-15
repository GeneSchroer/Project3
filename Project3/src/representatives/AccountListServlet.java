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

import beans.Account;
import beans.Client;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns={"/representatives/accountList"})
public class AccountListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public AccountListServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		int clientId = Integer.parseInt(request.getParameter("id"));
		Client client = null;
		List<Account> accountList=null;
		try {
			client = RepresentativeUtils.findClient(conn, clientId);
			accountList = RepresentativeUtils.getAccountList(conn, clientId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("client", client);
		request.setAttribute("accountList", accountList);
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/representatives/accountListView.jsp");
		dispatcher.forward(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doGet(request, response);
	}
}
