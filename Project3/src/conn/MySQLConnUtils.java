package conn;
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class MySQLConnUtils {
 
public static Connection getMySQLConnection()
        throws ClassNotFoundException, SQLException {
  
    // Note: Change the connection parameters accordingly.
    String hostName = "localhost";
    String dbName = "project3";
    String userName = "root";
    String password = "BraveNewWorld1";
    return getMySQLConnection(hostName, dbName, userName, password);
}
 
	public static Connection getMySQLConnection(String hostName, String dbName,
	        String userName, String password) throws SQLException, ClassNotFoundException {
	    
	    // Declare the class Driver for MySQL DB
	    // This is necessary with Java 5 (or older)
	    // Java6 (or newer) automatically find the appropriate driver.
	    // If you use Java> 5, then this line is not needed.
	    Class.forName("com.mysql.jdbc.Driver");
	 
	 
	    // URL Connection for MySQL
	    // Example: jdbc:mysql://localhost:3306/simplehr
	    String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?allowMultiQueries=true";
	 
	    Connection conn = DriverManager.getConnection(connectionURL, userName,
	            password);
	    return conn;
	}
	public static boolean backupDatabase(String fileName) throws ClassNotFoundException, IOException, InterruptedException{
		Class.forName("com.mysql.jdbc.Driver");
		String command = "\"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump\" --databases -u root --password=BraveNewWorld1 project3 --result-file=" + fileName;
		Process runtimeProcess=Runtime.getRuntime().exec(command);
		int processComplete= runtimeProcess.waitFor();
		if(processComplete==0)
			return true;
		else return false;
	}
}