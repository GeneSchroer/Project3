package beans;

import java.io.Serializable;

public class UserAccount implements Serializable {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PERSON_CLIENT ="C";
	   public static final String PERSON_MANAGER = "M";
	   public static final String PERSON_REP = "R"; 
	   private String userName;
	   private String password;
	   private String userType;
	    
	 
	   public UserAccount(String userName, String password, String userType) {
	        this.userName = userName;
	        this.password = password;
	        this.userType = userType;
	   }
	    
	   public String getUserName() {
	       return userName;
	   }
	 
	   public void setUserName(String userName) {
	       this.userName = userName;
	   }
	 
	   public String getPassword() {
	       return password;
	   }
	 
	   public void setPassword(String password) {
	       this.password = password;
	   }
	   public String getUserType(){
		   return userType;
	   }
	   public void setUserType(String userType){
		   this.userType = userType;
	   }
}
