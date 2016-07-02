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

@WebFilter(filterName="managerFilter", urlPatterns={"/managers","/managers/*"})
public class ManagerFilter implements Filter {
	
	 public ManagerFilter(){
	 
	 }
	  @Override
	  public void init(FilterConfig fConfig) throws ServletException{
	  
	  }
	  @Override
	  public void destroy(){
	  
	  }
	  @Override
	  public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
	  throws IOException, ServletException{
	  	HttpServletRequest httpRequest = (HttpServletRequest) request;
	  	HttpServletResponse httpResponse = (HttpServletResponse) response;
	  	HttpSession session = httpRequest.getSession();
	  	UserAccount user = (UserAccount) session.getAttribute("loginedUser");
	  	if(user == null || !user.getUserType().equals("Manager")){
	  		httpResponse.sendRedirect(httpRequest.getContextPath() + "/invalidAccess" );
	  		
	  	}
	  	else
	  		chain.doFilter(request, response);
	  	
	  }
}
