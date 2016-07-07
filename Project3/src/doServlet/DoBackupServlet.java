package doServlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conn.ConnectionUtils;
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
		
		// Used to see if the default checkbox was checked.
		String defaultName = request.getParameter("defaultLocation");
		
		File file = null;	
		//holds an error message if a backup cannot be created
		String errorStrBackup=null;
		
		//boolean to determine if the database was backed up
		boolean backedUp=false;
		
		
		if(defaultName !=null && defaultName.equals("setToDefault")){
			try {
				backedUp = ConnectionUtils.defaultBackup();
			} catch (ClassNotFoundException | InterruptedException e) {
				backedUp=false;
				e.printStackTrace();
			}
		}
		else
		{
			file = new File(request.getParameter("fileName"));
		
			if (!file.exists()){
				try {
					backedUp = ConnectionUtils.backupDatabase(file.getAbsolutePath());
				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					backedUp=false;
					e.printStackTrace();
					errorStrBackup =e.getMessage();
				}
			}
			else{
					backedUp=false;
					errorStrBackup="Error: File may already exist";
			}
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
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
}
