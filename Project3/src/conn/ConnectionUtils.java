package conn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class ConnectionUtils {
	public static Connection getConnection() 
				throws ClassNotFoundException, SQLException{
		
		return MySQLConnUtils.getMySQLConnection();
		
	}
	
	public static void closeQuietly(Connection conn){
		try{
			conn.close();
		} catch(Exception e) {
			
		}
	}
	public static void rollbackQuietly(Connection conn){
		try{
			conn.rollback();
		} catch(Exception e){
			
		}
	}

	public static boolean backupDatabase(String fileName) throws ClassNotFoundException, IOException, InterruptedException {
		return MySQLConnUtils.backupDatabase(fileName);
	}

	public static boolean defaultBackup() throws ClassNotFoundException, IOException, InterruptedException {
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		String defaultLocation = "C:\\Users\\Work\\Project3Backup" 
		+ date.toString()  
		+ "--" + time.getHours() + "-" + time.getMinutes() + "-" + time.getSeconds() + ".sql";
		
		
		return backupDatabase(defaultLocation);
	}
}
