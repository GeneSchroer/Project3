package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserAccount;

@WebFilter(filterName="customerFilter", urlPatterns={"/customers", "/customers/*"})
public class CustomerFilter implements Filter {

	public CustomerFilter(){
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		 
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		UserAccount user = (UserAccount) session.getAttribute("loginedUser");
		if(user == null || !user.getUserType().equals("Customer")){
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/invalidAccess");
		}
		else
			chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
