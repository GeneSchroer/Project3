package utils;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserAccount;
public class LoginUtils {
	  public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		  
	      String sql = "SELECT * FROM UserAccount"
	              + " WHERE UserName = ? AND Password= ?";
	 
	      PreparedStatement pstm = conn.prepareStatement(sql);
	      pstm.setString(1, userName);
	      pstm.setString(2, password);
	      ResultSet rs = pstm.executeQuery();
	 
	      if (rs.next()) {
	          UserAccount user = new UserAccount(
	        		  rs.getString("UserName"),
	        		  rs.getString("Password"),
	        		  rs.getString("UserType"),
	          		  rs.getInt("Id"));
	          return user;
	      }
	      return null;
	  }
	 

	 
	   public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	 
	   private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
	 
	    
	   // Store Connection in request attribute.
	   // (Information stored only exist during requests)
	   public static void storeConnection(ServletRequest request, Connection conn) {
	       request.setAttribute(ATT_NAME_CONNECTION, conn);
	   }
	 
	   // Get the Connection object has been stored in one attribute of the request.
	   public static Connection getStoredConnection(ServletRequest request) {
	       Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
	       return conn;
	   }
	 
	   // Store user info in Session.
	   public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
	 
	       // On the JSP can access ${loginedUser}
	       session.setAttribute("loginedUser", loginedUser);
	   }
	 
	 
	   // Get the user information stored in the session.
	   public static UserAccount getLoginedUser(HttpSession session) {
	       UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
	       return loginedUser;
	   }
	 
	 
	   // Store info in Cookie
	   public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
	       System.out.println("Store user cookie");
	       Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
	 
	       // 1 day (Convert to seconds)
	       cookieUserName.setMaxAge(24 * 60 * 60);
	       response.addCookie(cookieUserName);
	   }
	 
	   public static String getUserNameInCookie(HttpServletRequest request) {
	       Cookie[] cookies = request.getCookies();
	       if (cookies != null) {
	           for (Cookie cookie : cookies) {
	               if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
	                   return cookie.getValue();
	               }
	           }
	       }
	       return null;
	   }
	 
	 
	   // Delete cookie.
	   public static void deleteUserCookie(HttpServletResponse response) {
	       Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
	 
	       // 0 seconds (Expires immediately)
	       cookieUserName.setMaxAge(0);
	       response.addCookie(cookieUserName);
	   }

	public static int getId(HttpSession session) {
		
		return ((UserAccount)session.getAttribute("loginedUser")).getId();
	}
	
	/* Change your password*/
	public static void changePassword(Connection conn, String userName, String password1) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "UPDATE UserAccount SET Password = ? WHERE UserName = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, password1);
		pstm.setString(2, userName);
		pstm.executeUpdate();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
	}
	 
	}