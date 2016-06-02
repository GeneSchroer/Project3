package doServlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.MyUtils;

@WebServlet(urlPatterns = {"/doRecordOrder"})
public class DoRecordOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DoRecordOrderServlet(){
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		System.out.println(request.getParameter("stockBox"));
	
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INT/views/recordOrder.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
}
