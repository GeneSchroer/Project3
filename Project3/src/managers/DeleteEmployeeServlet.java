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

import beans.Employee;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/managers/deleteEmployee" })
public class DeleteEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public DeleteEmployeeServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
 
        int employeeId = Integer.parseInt(request.getParameter("id"));
        Employee employee = null;
        boolean hasClients = false;
        try {
        	employee = ManagerUtils.findEmployee(conn, employeeId);
        	hasClients = ManagerUtils.hasClients(conn, employeeId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
         	
        if(employee!=null){
        	request.setAttribute("employee", employee);
        	request.setAttribute("hasClients", hasClients);
        }
        RequestDispatcher dispatcher = request.getServletContext()
           	.getRequestDispatcher("/WEB-INF/views/managers/deleteEmployeeView.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
