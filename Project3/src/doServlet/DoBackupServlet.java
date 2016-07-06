package doServlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conn.MySQLConnUtils;
import utils.MyUtils;

@WebServlet(urlPatterns={"/managers/doBackup"})
public class DoBackupServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		File  file = new File(request.getParameter("fileName"));
		
		
		String errorStrBackup=null;
		boolean backedUp=false;
		if (!file.exists()){
		try {
			backedUp =MySQLConnUtils.backupDatabase(file.getAbsolutePath());
		} catch (ClassNotFoundException e) {
			backedUp=false;
			e.printStackTrace();
			errorStrBackup =e.getMessage();
		} catch (InterruptedException e) {
			backedUp=false;
			e.printStackTrace();
			errorStrBackup = e.getMessage();
		}
		
			if(backedUp)
				response.sendRedirect(request.getContextPath() + "/managers");
			else{
				errorStrBackup= "Error: Could not create backup. You may be unable to create a file in that directory. Please try again.";
				request.setAttribute("errorStrBackup", errorStrBackup);
				RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/managers/backupView.jsp");
				dispatcher.forward(request, response);
			}
		}
		else{
			errorStrBackup="Error: File may already exist";
			request.setAttribute("errorStrBackup", errorStrBackup);
			RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/managers/backupView.jsp");
			dispatcher.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
}
