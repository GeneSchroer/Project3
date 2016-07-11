package managers;

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

	import beans.Stock;
	import utils.MyUtils;
	import utils.RepresentativeUtils;

	@WebServlet(urlPatterns = {"/managers/orderList"})
	public class OrderListServlet extends HttpServlet{
		private static final long serialVersionUID = 1L;
		public OrderListServlet(){
			super();
		}
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException{
			Connection conn = MyUtils.getStoredConnection(request);
			String errorString = null;
			List<Stock> list = null; //list of all stocks
			try{
				//get list of stocks
				list=RepresentativeUtils.getStockList(conn);
			}catch(SQLException e){
				e.printStackTrace();
				errorString = e.getMessage();
			}
			
			// pass all values to page
			request.setAttribute("errorSting", errorString);
			request.setAttribute("stockList", list);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/managers/orderListView.jsp");
			dispatcher.forward(request, response);
			
			
		}
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException{
			doGet(request, response);
		}
	}


