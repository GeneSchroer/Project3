
package utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Employee;
import beans.FullOrder;
import beans.HighRoller;
import beans.Orders;
import beans.Person;
import beans.SalesReport;
import beans.Stock;
//Add employee information
public class ManagerUtils {

	
	public static List<Employee> getEmployeeList(Connection conn) throws SQLException{
		String sql = "SELECT E.*,P.* "
				+ " FROM Employee E, Person P "
				+ "	WHERE P.SSN=E.SSN";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		List<Employee> list = new ArrayList<Employee>();
		while(rs.next()){
			Employee employee = new Employee(
					rs.getInt("SSN"), 
					rs.getString("LastName"), 
					rs.getString("FirstName"), 
					rs.getString("Address"), 
					rs.getInt("ZipCode"), 
					rs.getString("Telephone"),
					rs.getInt("Id"), 
					rs.getDate("StartDate"), 
					rs.getInt("HourlyRate"));
			list.add(employee);
		}
		return list;
	}
	
	public static void addEmployee(Connection conn, Employee employee) throws SQLException{
		String sql = "INSERT INTO Person(SSN, LastName, FirstName, Address, ZipCode,"
				+ " Telephone) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setInt		(1, employee.getSSN());
		pstm.setString	(2, employee.getLastName());
		pstm.setString	(3, employee.getFirstName());
		pstm.setString	(4, employee.getAddress());
		pstm.setInt		(5, employee.getZipCode());
		pstm.setString	(6, employee.getTelephone());
		pstm.executeUpdate();
		
		sql = "INSERT INTO Employee(ID, SSN, StartDate, HourlyRate)"
			+ " VALUES (?, ?, ?, ?)";
		
		pstm = conn.prepareStatement(sql);
		
		pstm.setInt		(1, employee.getId());
		pstm.setInt		(2, employee.getSSN());
		pstm.setDate	(3, employee.getStartDate());
		pstm.setFloat	(4, employee.getHourlyRate());
		pstm.executeUpdate();
	}

	public static Employee findEmployee(Connection conn, int id) throws SQLException {
		String sql = "SELECT E.* "
					+ " FROM Employee E "
					+ "WHERE E.Id = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()){
			int eId = rs.getInt("id");
			int SSN = rs.getInt("SSN");
			Date startDate = rs.getDate("startDate");
			int hourlyRate = rs.getInt("hourlyRate");
		
			Person person = findPerson(conn, SSN);
			String lastName = person.getLastName();
			String firstName = person.getFirstName();
			String address = person.getAddress();
			int zipCode = person.getZipCode();
			String telephone = person.getTelephone();
			Employee employee = new Employee(SSN, lastName, firstName, address, 
				zipCode, telephone, eId, startDate, hourlyRate);
			return employee;
		}
		else
			return null;
	}

	public static Person findPerson(Connection conn, int PSSN) throws SQLException {
	String sql = "SELECT P.* FROM Person P WHERE P.SSN = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, PSSN);
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()){
			int SSN = rs.getInt("SSN");
			String lastName = rs.getString("LastName");
			String firstName = rs.getString("FirstName");
			String address = rs.getString("Address");
			int zipCode = rs.getInt("ZipCode");
			String telephone = rs.getString("Telephone");
			Person person = new Person(SSN, lastName, firstName, address, 
				zipCode, telephone);
			return person;
		}
		else		
			return null;
	}

	public static void updateEmployee(Connection conn, Employee employee) throws SQLException{
		String sql = "UPDATE EmployeeInfo "
				+ "SET LastName=?, FirstName=?, Address=?, ZipCode=?,"
				+ " Telephone=? "
				+ " WHERE Id=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString	(1, employee.getLastName());
		pstm.setString	(2, employee.getFirstName());
		pstm.setString	(3, employee.getAddress());
		pstm.setInt		(4, employee.getZipCode());
		pstm.setString	(5, employee.getTelephone());
		pstm.setInt		(6, employee.getId());
		pstm.executeUpdate();
		
		sql = "UPDATE EmployeeInfo SET HourlyRate = ? WHERE Id = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, employee.getHourlyRate());
		pstm.setInt(2, employee.getId());
	}
	public static List<Stock> getStockList(Connection conn) throws SQLException{
			String sql = "SELECT * FROM Stock";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			List<Stock> list = new ArrayList<Stock>();
			while(rs.next()){
				String stockSymbol = rs.getString("stockSymbol");
				String companyName = rs.getString("CompanyName");
				String type = rs.getString("Type");
				float pricePerShare = rs.getFloat("PricePerShare");
				Stock stock = new Stock(stockSymbol, companyName, type, pricePerShare);
				list.add(stock);
				
			}
			return list;
	}
	
	// Produce a comprehensive listing of all stocks
	
	// "select S.* from Stock S order by S.Type, S.StockSymbol;"
	
	
	
	
	// Produce a summary listing of revenue generated by a particular stock, stock type, or customer
	// By particular stock
	// "select S.StockSymbol, S.CompanyName, sum(Trns.Fee) as Revenue"
	//	+ " from Stock S, Trade Trd, Transaction Trns"
	//		+ " where S.StockSymbol = ? and Trd.StockId = S.StockSymbol and Trns.Id = Trd.TransactionId"
	//			+ " and trns.DateTime not null group by S.StockSymbol, S.CompanyName
	
	// by stock type
	// "select S.Type, sum(Trns.Fee) from Stock S, Trade Trd, Transaction Trns"
	//	+ " where S.Type = ? and Trd.StockId = S.StockSymbol and Trns.Id = Trd.TransactionId"
	//		+ " and trns.DateTime not null group by S.Type"
	
	// by a particular customer
	// select C.Id, P.LastName, P.FirstName, SUM(Trns.Free) from Trade Trd, Transaction Trns"
	// + " where C.Id = ? and P.SSN = C.Id and A.Clinet = C.Id and Trd.AccountId = A.Id"
	//	+ " and Trns.Id = Trd.TransactionId and Trns.DateTime not null"
	
	// Determine which customer representative generated most total revenue
	// "select P.LastName, P.FirstName, E.Id, sum(Trns.Fee) as Revenue" 
	// + " from Employee E, Person P, Trade Trd, Transaction Trns:
	//	+ " where E.SSN = P.SSN and Trd.BrokerId = E.Id and Trns.Id = Trd.TransactionId"
	//		+ " and Trns.DateTime not null group by E.Id order by desc Revenue limit 1"
	
	// Determine which customer generated most total revenue
	// "select P.LastName, P.FirstName, C.Id, sum(Trns.Fee) as Revenue"
	// + " Account A, Client C, Person P, Trade Trd, Transaction Trns"
	//	+ " where C.Id = P.SSN and A.Client = C.Id and Trd.AccountId = A.Id"
	//		+ " and Trns.Id = Trd.TransactionId and Trns.DateTime not null group by C.Id"
	//			+ " order by desc Revenue limit 1"
	
	// Produce a list of most actively traded stocks
	// "select count(*) as TimesTraded, S.StockSymbol, S.CompanyName"
	// + " from Stock S, Trade Trd, Transaction Trns"
	// 	+ " where S.StockSymbol = Trd.StockId and Trns.Id = Trd.TransactionId"
	// 		+ " and Trns.DateTime not null group by S.StockSymbol order by desc TimesTraded limit 20"
	
	
	public static Stock findStock(Connection conn, String stockSymbol) throws SQLException {
		String sql = "SELECT * FROM Stock WHERE StockSymbol = ?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, stockSymbol);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()){
				String companyName = rs.getString("companyName");
				String type = rs.getString("type");
				float pricePerShare= rs.getFloat("pricePerShare");
				Stock stock = new Stock(stockSymbol, companyName, type, pricePerShare);
				return stock;
			}
			else		
				return null;
		}	
	public static void updateSharePrice(Connection conn, Stock stock) throws SQLException{
			String sql = "UPDATE Stock"
					+ " SET pricePerShare = ?"
					+ " WHERE stockSymbol = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setFloat	(1, stock.getPricePerShare());
			pstm.setString	(2, stock.getStockSymbol());
			pstm.executeUpdate();
		}
	
	public static void deleteEmployee(Connection conn, int id) throws SQLException {
		String sql = "SELECT SSN"
				+ " FROM Employee"
				+ " WHERE id = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int SSN = rs.getInt("SSN");
		
		sql = "DELETE FROM Employee WHERE id = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		pstm.executeUpdate();
		
		
		sql = "DELETE FROM Person WHERE SSN = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, SSN);
		pstm.executeUpdate();
	}
	
	public static List<SalesReport> getSalesReport(Connection conn, String year, String month) throws SQLException{
		String sql = "SELECT Trns.*,	S.StockSymbol, S.CompanyName, S.Type, O.NumShares, O.OrderType, O.PriceType"
				+ " FROM Orders O, Stock S, Trade Trd, Transaction Trns"
				+ " WHERE YEAR(Trns.DateTime)=? AND MONTH(Trns.DateTime)=?"
				+ " AND	Trd.TransactionId = Trns.Id	AND	Trd.OrderId = O.Id"
				+ " AND	Trd.StockId = S.StockSymbol"
				+ " ORDER BY Trns.DateTime DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, year);
		pstm.setString(2, month);
		ResultSet rs = pstm.executeQuery();
		List<SalesReport> list = new ArrayList<SalesReport>();
		while(rs.next()){
			int id = rs.getInt("Id");
			int fee =rs.getInt("Fee");
			Timestamp dateTime = rs.getTimestamp("DateTime");
			float pricePerShare =rs.getFloat("PricePerShare");
			String stockSymbol =rs.getString("StockSymbol");
			String companyName = rs.getString("CompanyName");
			String type = rs.getString("Type");
			int numShares = rs.getInt("NumShares");
			String orderType = rs.getString("OrderType");
			String priceType = rs.getString("PriceType");
			SalesReport sr = new SalesReport(id, fee, dateTime, pricePerShare,
					stockSymbol, companyName, type, numShares, orderType, priceType);
			list.add(sr);
		}
		return list;
	}
	
	//Produce a list of orders by stock symbol or by customer name
	public static List<FullOrder>getOrderList(Connection conn, String lastName, String firstName, String stockSymbol) throws SQLException{
		String sql;
		PreparedStatement pstm;
		int parameter=1;
		//if we only select based on stockSymbol, we can use a simpler statement
		if(lastName==null && firstName==null){
			sql = "SELECT DISTINCT O.*, Trd.AccountId, Trd.BrokerId,"
					+ " Trd.StockId, Trns.Id AS TransactionId,"
					+ " Trns.Fee, Trns.DateTime AS FinalDateTime,"
					+ " Trns.PricePerShare as FinalPricePerShare"
					+ " FROM Orders O, Trade Trd, Transaction Trns"
					+ " WHERE Trd.OrderId = O.Id AND Trd.TransactionId = Trns.Id ";
		}
		else{
			sql = "SELECT O.*, Trd.AccountId, Trd.BrokerId,"
					+ " Trd.StockId, Trns.Id As TransactionId,"
					+ " Trns.Fee, Trns.DateTime AS FinalDateTime,"
					+ " Trns.PricePerShare AS FinalPricePerShare"
					+ " FROM Account A, Client C, Orders O, Person P, Trade Trd, Transaction Trns"
					+ " WHERE C.Id = P.SSN"
					+ " AND A.Client = C.Id"
					+ " AND Trd.AccountId = A.Id"
					+ " AND O.Id = Trd.OrderId"
					+ " AND Trd.TransactionId = Trns.Id";
		}
		if(lastName!=null){
			sql += " AND P.LastName LIKE ?";
		}
		if(firstName!=null){
			sql += " AND P.FirstName LIKE ?";
		}
		if(stockSymbol!=null){
			sql += " AND Trd.StockId = ?";
		}
		pstm = conn.prepareStatement(sql);
		
		if(lastName!=null){
			pstm.setString(parameter++, lastName);
		}
		if(firstName!=null){
			pstm.setString(parameter++, firstName);
		}
		if(stockSymbol!=null){
			pstm.setString(parameter, stockSymbol);
		}
		
		
		List<FullOrder>list = new ArrayList<FullOrder>();
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			FullOrder order = new FullOrder(
					rs.getInt("NumShares"), 
					rs.getFloat("PricePerShare"),
					rs.getInt("Id"),  
					rs.getTimestamp("DateTime"), 
					rs.getDouble("Percentage"), 
					rs.getString("PriceType"), 
					rs.getString("OrderType"), 
					rs.getInt("AccountId"),
					rs.getInt("BrokerId"),
					rs.getString("StockId"),
					rs.getInt("Fee"), 
					rs.getTimestamp("FinalDateTime"), 
					rs.getFloat("FinalPricePerShare"));
			list.add(order);
		}
		return list;
	}
	
	public static List<String> getStockTypes(Connection conn) throws SQLException{
		String sql = "SELECT DISTINCT Type FROM Stock";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<String> list = new ArrayList<String>();
		while(rs.next()){
			list.add(rs.getString("Type"));
		}
		return list;
		
	}
	public static void getSummaryListing
	(Connection conn, String lastName, String firstName, String type, String stockSymbol){
		String sql;
		sql="SELECT S.*, (SUM(Trns.PricePerShare) * SUM(O.NumShares)) AS Revenue"
				+ " FRO Stock S, Trade Trd, Transaction Trns,"
				+ " WHERE Trd.StockId=S.StockSymbol"
				+ " AND Trd.TransactionId = Trns.Id"
				+ " And Trns.DateTime IS NOT NULL" ;
				
		
		sql ="SELECT C.Id, P.LastName, P.FirstName, (SUM(Trns.PricePerShare) * SUM(O.NumShares)) AS Revenue"
				+ " FROM Orders O, Stock S, Trade Trd, Transaction Trns"
				+ " WHERE P.SSN = C.Id"
				+ " AND A.Client = C.Id"
				+ " AND Trd.AccountId = A.Id"
				+ " AND Trns.Id = Trd.TransactionId"
				+ " And Trns.DateTime IS NOT NULL";
		
		
	}
	
	public static HighRoller findCustomerWithMostRevenue(Connection conn) throws SQLException{
		String sql = "SELECT P.LastName, P.FirstName, Trd.BrokerId,"
				+ " (SUM(Trns.Price)*SUM(O.NumShares)) AS Revenue"
				+ " FROM Employee E, Orders O, Person P, Trade Trd, Transaction Trns"
				+ " WHERE E.SSN = P.SSN"
				+ " AND Trd.OrderId = O.Id"
				+ " AND Trd.BrokerId = E.Id"
				+ " AND	Trd.TransactionId = Trns.Id"
				+ " AND Trns.DateTime IS NOT NULL"
				+ " GROUP BY E.Id"
				+ " ORDER BUY DESC Revenue"
				+ " LIMIT 1";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		HighRoller hr = null;
		if(rs.next()){
			hr = new HighRoller(
					rs.getString("LastName"),
					rs.getString("FirstName"),
					rs.getInt("Id"),
					rs.getFloat("Revenue"));
			return hr;
		}
		return null;
	}
	public static HighRoller findRepresentativeWithMostRevenue(Connection conn) throws SQLException{
		String sql = "SELECT P.LastName, P.FirstName, Trd.BrokerId,"
				+ " (SUM(Trns.Price)*SUM(O.NumShares)) AS Revenue"
				+ " FROM Employee E, Orders O, Person P, Trade Trd, Transaction Trns"
				+ " WHERE E.SSN = P.SSN"
				+ " AND Trd.OrderId = O.Id"
				+ " AND Trd.BrokerId = E.Id"
				+ " AND	Trd.TransactionId = Trns.Id"
				+ " AND Trns.DateTime IS NOT NULL"
				+ " GROUP BY E.Id"
				+ " ORDER BUY DESC Revenue"
				+ " LIMIT 1";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		HighRoller hr = null;
		if(rs.next()){
			hr = new HighRoller(
					rs.getString("LastName"),
					rs.getString("FirstName"),
					rs.getInt("Id"),
					rs.getFloat("Revenue"));
			return hr;
		}
		return null;
	}

}

