package representatives;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.RepresentativeUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/representatives/deleteClient" })
public class DeleteClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public DeleteClientServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
 
        int clientId = Integer.parseInt(request.getParameter("id"));
 
            // Store the information in the request attribute, before forward to views.

        request.setAttribute("clientId", clientId);
        RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/representatives/deleteClientView.jsp");
            dispatcher.forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
