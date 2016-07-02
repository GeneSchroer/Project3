
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
import beans.Location;
import beans.Orders;
import beans.Person;
import beans.SalesReport;
import beans.Stock;
import beans.SummaryListing;
import beans.UserAccount;
//Add employee information
public class ManagerUtils {

	
	public static List<Employee> getEmployeeList(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ "	SELECT E.*,P.*"
				+ " FROM Employee E, Person P "
				+ "	WHERE P.SSN=E.SSN"
				+ " ORDER BY P.LastName ASC;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		List<Employee> list = new ArrayList<Employee>();
		while(rs.next()){
			Employee employee = new Employee(
					rs.getInt("SSN"), 
					rs.getString("FirstName"), 
					rs.getString("LastName"), 
					rs.getString("Address"), 
					rs.getInt("ZipCode"), 
					rs.getString("Telephone"),
					rs.getInt("Id"), 
					rs.getDate("StartDate"), 
					rs.getInt("HourlyRate"));
			list.add(employee);
		}
		conn.commit();
		return list;
	}
	
	public static void addEmployee(Connection conn, Employee employee, Location location, UserAccount user) throws SQLException{
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		
		
		sql = "INSERT IGNORE INTO Location(ZipCode, City, State)"
				+ " VALUES(?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, location.getZipCode());
		pstm.setString(2, location.getCity());
		pstm.setString(3, location.getState());
		pstm.executeUpdate();
		
		sql = "INSERT INTO Person(SSN, LastName, FirstName, Address, ZipCode,"
				+ " Telephone) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		
		pstm.setInt		(1, employee.getSSN());
		pstm.setString	(2, employee.getLastName());
		pstm.setString	(3, employee.getFirstName());
		pstm.setString	(4, employee.getAddress());
		pstm.setInt		(5, employee.getZipCode());
		pstm.setString	(6, employee.getTelephone());
		pstm.executeUpdate();
		
		sql = "INSERT INTO Employee(Id, SSN, StartDate, HourlyRate)"
			+ " VALUES (?, ?, ?, ?)";
		
		pstm = conn.prepareStatement(sql);
		
		pstm.setInt		(1, employee.getId());
		pstm.setInt		(2, employee.getSSN());
		pstm.setDate	(3, employee.getStartDate());
		pstm.setFloat	(4, employee.getHourlyRate());
		pstm.executeUpdate();
		
		sql = "INSERT INTO UserAccount(UserName, Password, UserType, Id)"
				+ " VALUES(?, ?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, user.getUserName());
		pstm.setString(2, user.getPassword());
		pstm.setString(3, user.getUserType());
		pstm.setInt(4, user.getId());
		pstm.executeUpdate();
		
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
	}

	public static Employee findEmployee(Connection conn, int id) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		
		sql = "SELECT E.* "
				+ " FROM Employee E "
				+ "WHERE E.Id = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		ResultSet rs = pstm.executeQuery();
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
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
			Employee employee = new Employee(SSN, firstName, lastName, address, 
				zipCode, telephone, eId, startDate, hourlyRate);
			return employee;
		}
		else
			return null;
	}

	public static Person findPerson(Connection conn, int PSSN) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		
		sql = "SELECT P.* FROM Person P WHERE P.SSN = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, PSSN);
		ResultSet rs = pstm.executeQuery()
				;
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
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

	public static void updateEmployee(Connection conn, Employee employee, Location location) throws SQLException{
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "INSERT IGNORE INTO Location(ZipCode, City, State)"
				+ "VALUES(?, ?, ?)";
		pstm = conn.prepareCall(sql);
		pstm.setInt(1, location.getZipCode());
		pstm.setString(2, location.getCity());
		pstm.setString(3, location.getState());
		pstm.executeUpdate();
		
		sql = "UPDATE EmployeeInfo "
				+ "SET LastName=?, FirstName=?, Address=?, ZipCode=?,"
				+ " Telephone=? "
				+ " WHERE Id=?";
		pstm = conn.prepareStatement(sql);
		
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
		pstm.executeUpdate();
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
	}
	public static List<Stock> getStockList(Connection conn) throws SQLException{
			String sql = "START TRANSACTION";
			PreparedStatement pstm = conn.prepareCall(sql);
			pstm.execute();
			sql = "SELECT * FROM Stock";
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			List<Stock> list = new ArrayList<Stock>();
			while(rs.next()){
				Stock stock = new Stock(
								rs.getString("StockSymbol"),
								rs.getString("CompanyName"),
								rs.getString("Type"),
								rs.getFloat("PricePerShare"),
								rs.getInt("NumShares"));
				list.add(stock);
				
			}
			pstm = conn.prepareStatement("COMMIT");
			pstm.execute();
			return list;
	}
	
	
	
	
	
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
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT * FROM Stock WHERE StockSymbol = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		ResultSet rs = pstm.executeQuery();
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
		if(rs.next()){
			Stock stock = new Stock(
						rs.getString("StockSymbol"),
						rs.getString("CompanyName"),
						rs.getString("Type"),
						rs.getFloat("PricePerShare"),
						rs.getInt("NumShares")
						);
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT Trns.*,	S.StockSymbol, S.CompanyName, S.Type, O.NumShares, O.OrderType, O.PriceType"
				+ " FROM Orders O, Stock S, Trade Trd, Transaction Trns"
				+ " WHERE YEAR(Trns.DateTime)=? AND MONTH(Trns.DateTime)=?"
				+ " AND	Trd.TransactionId = Trns.Id	AND	Trd.OrderId = O.Id"
				+ " AND	Trd.StockId = S.StockSymbol"
				+ " ORDER BY Trns.DateTime DESC;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, year);
		pstm.setString(2, month);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		List<SalesReport> list = new ArrayList<SalesReport>();
		while(rs.next()){
			Integer id = rs.getInt("Id");
			Float fee = rs.getFloat("Fee");
			Timestamp dateTime = rs.getTimestamp("DateTime");
			Float pricePerShare =rs.getFloat("PricePerShare");
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
		conn.commit();
		return list;
	}
	
	//Produce a list of orders by stock symbol or by customer name
	public static List<FullOrder>getOrderList(Connection conn, String lastName, String firstName, String stockSymbol) throws SQLException{
		String sql;
		PreparedStatement pstm;
		int parameter=1;
		//if we only select based on stockSymbol, we can use a simpler statement
		if(lastName==null && firstName==null){
			sql = "SELECT DISTINCT O.*,  Trd.AccountId, Trd.BrokerId,"
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
					rs.getInt("TransactionId"),
					rs.getFloat("Fee"), 
					rs.getTimestamp("FinalDateTime"), 
					rs.getFloat("FinalPricePerShare"),
					rs.getInt("AccountId"),
					rs.getInt("BrokerId"),
					rs.getString("StockId")
					);
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
		String sql="SELECT S.*, (SUM(Trns.PricePerShare) * SUM(O.NumShares)) AS Revenue"
				+ " FROM Stock S, Trade Trd, Transaction Trns,"
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
		String sql = "SELECT P.LastName, P.FirstName, C.Id,"
				+ " (SUM(Trns.PricePerShare)*SUM(O.NumShares)) AS Revenue"
				+ " FROM Account A, Client C, Orders O, Person P, Trade Trd, Transaction Trns"
				+ " WHERE C.Id = P.SSN"
				+ " AND A.Client = C.Id"
				+ " AND Trd.AccountId = A.Id"
				+ " AND Trd.OrderId = O.Id"
				+ " AND	Trd.TransactionId = Trns.Id"
				+ " AND Trns.DateTime IS NOT NULL"
				+ " GROUP BY C.Id"
				+ " ORDER BY Revenue DESC"
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
		String sql = "SELECT P.LastName, P.FirstName, E.Id,"
				+ " (SUM(Trns.PricePerShare)*SUM(O.NumShares)) AS Revenue"
				+ " FROM Employee E, Orders O, Person P, Trade Trd, Transaction Trns"
				+ " WHERE E.SSN = P.SSN"
				+ " AND Trd.OrderId = O.Id"
				+ " AND Trd.BrokerId = E.Id"
				+ " AND	Trd.TransactionId = Trns.Id"
				+ " AND Trns.DateTime IS NOT NULL"
				+ " GROUP BY E.Id"
				+ " ORDER BY Revenue DESC"
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

	public static Location findLocation(Connection conn, Integer zipCode) throws SQLException {
		String sql = "SELECT * FROM Location L WHERE L.ZipCode = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, zipCode);
		ResultSet rs = pstm.executeQuery();
		if(rs.next()){
			Location location = new Location(
								rs.getInt("ZipCode"),
								rs.getString("City"),
								rs.getString("State"));
			return location;
		}
		return null;
	}

	public static void addStock(Connection conn, Stock stock) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		
		sql = "INSERT INTO Stock(StockSymbol, CompanyName, Type, PricePerShare, NumShares)"
				+ " VALUES(?, ?, ?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, stock.getStockSymbol());
		pstm.setString(2, stock.getCompanyName());
		pstm.setString(3, stock.getType());
		pstm.setFloat(4, stock.getPricePerShare());
		pstm.setInt(5, stock.getNumShares());
		pstm.executeUpdate();
		
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
	}

	public static void editStockName(Connection conn, Stock stock) throws SQLException {
		String sql = "UPDATE Stock"
				+ " SET CompanyName = ?, TYPE = ?"
				+ " WHERE StockSymbol = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stock.getCompanyName());
		pstm.setString(2, stock.getType());
		pstm.setString(3, stock.getStockSymbol());
		pstm.executeUpdate();
		
	}
	public static List<Stock> getMostActivelyTradedStock(Connection conn) throws SQLException{
		String sql = "SELECT COUNT(*) AS TimesTrades, S.*"
				+ " FROM Stock S, Trade Trd"
				+ " WHERE S.StockSymbol = Trd.StockId"
				+ " GROUPS BY S.StockSymbol"
				+ " ORDER BY TimesTraded DESC"
				+ " LIMIT BY 20";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Stock> list = new ArrayList<Stock>();
		while(rs.next()){
			Stock stock = new Stock(
							rs.getString("StockSymbol"),
							rs.getString("CompanyName"),
							rs.getString("Type"),
							rs.getFloat("PricePerShare")
							);
			list.add(stock);
		}
		return list;
	}
	
	public static int getMostRecentEmployeeId(Connection conn) throws SQLException{
		String sql = "SELECT Id FROM Employee ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("Id");
		else 
			return 0;
	}

	public static void updateStock(Connection conn, Stock stock) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "UPDATE Stock"
				+ " SET PricePerShare = ?, NumShares = ?"
				+ " WHERE StockSymbol = ?";
		pstm = conn.prepareStatement(sql);
		System.out.println(stock.getPricePerShare());
		System.out.println(stock.getNumShares());
		pstm.setFloat	(1, stock.getPricePerShare());
		pstm.setInt		(2, stock.getNumShares());
		pstm.setString	(3, stock.getStockSymbol());
		pstm.executeUpdate();
		
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
	}

	public static SummaryListing getSummaryListingByStockSymbol(Connection conn, String stockSymbol) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT 	S.StockSymbol,"
				+ "			S.CompanyName,"
				+ "			SUM(Trns.Fee) AS Revenue"
				+ " FROM		Stock S,"
				+ "			Trade Trd,"
				+ "			Transaction Trns"
				+ " WHERE 	S.StockSymbol = ?"
				+ " AND		Trd.StockId = S.StockSymbol"
				+ " AND		Trns.Id = Trd.TransactionId"
				+ " AND		Trns.DateTime IS NOT NULL"
				+ " GROUP BY S.StockSymbol, S.CompanyName;"
				+ " COMMIT;";
		
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		conn.commit();
		if(rs.next()){
			SummaryListing sL = new SummaryListing(
									rs.getString("StockSymbol"),
									rs.getString("CompanyName"),
									rs.getFloat("Revenue"));
			return sL;
		}
		else
			return null;
		
	}

	public static SummaryListing getSummaryListingByStockType(Connection conn, String type) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
					+ " SELECT	S.Type,"
					+ " 		SUM(Trns.Fee) AS Revenue"
					+ " FROM	Stock S,"
					+ " 		Trade Trd,"
					+ "			Transaction Trns"
					+ " WHERE	S.Type = ?" 
					+ " AND		Trd.StockId = S.StockSymbol"
					+ " AND		Trns.Id = Trd.TransactionId"
					+ " AND		Trns.DateTime IS NOT NULL"
					+ " GROUP BY 	S.Type;"
					+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, type);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		if(rs.next()){
			SummaryListing sumList = new SummaryListing(
										rs.getString("Type"),
										rs.getFloat("Revenue")
										);
			return sumList;
		}
		else{
			return null;
		}
	}

	public static SummaryListing getSummaryListingByCustomerName(Connection conn, String firstName, String lastName) throws SQLException{
		String sql= "START TRANSACTION;"
					+ "SELECT	C.Id,"
					+ "			P.LastName,"
					+ "			P.FirstName,"
					+ "			SUM(Trns.Fee) AS Revenue"	
					+ " FROM 	Account A,"
					+ "			Client C,"
					+ "			Person P,"
					+ "			Trade Trd,"
					+ " 		Transaction Trns"
					
					+ "	WHERE 	P.LastName LIKE ?"
					+ " AND 	P.FirstName LIke ?"
					+ " AND		C.Id = P.SSN"
					+ "	AND		P.SSN = C.Id"
					+ "	AND		A.Client = C.Id"
					+ "	AND		Trd.AccountId = A.Id"
					+ "	AND		Trns.Id = Trd.TransactionId"
					+ "	AND		Trns.DateTime IS NOT NULL;"
					+ "	COMMIT;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, "%" + lastName + "%");
		pstm.setString(2, "%" + firstName + "%");
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		if(rs.next()){
			SummaryListing sumList = new SummaryListing(
										rs.getInt("Id"),
										rs.getString("LastName"),
										rs.getString("FirstName"),
										rs.getFloat("Revenue")
										);
			return sumList;
		}
		return null;
	}
		

}

