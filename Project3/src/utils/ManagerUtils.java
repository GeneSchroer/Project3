
package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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
import conn.MySQLConnUtils;
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " INSERT IGNORE INTO Location(ZipCode, City, State)"
				+ " VALUES(?, ?, ?);"
				+ " INSERT INTO Person(SSN, LastName, FirstName, Address, ZipCode,"
				+ " Telephone)"
				+ " VALUES (?, ?, ?, ?, ?, ?);"
				+ " INSERT INTO Employee(Id, SSN, StartDate, HourlyRate)"
				+ " VALUES (?, ?, ?, ?);"
				+ " INSERT INTO UserAccount(UserName, Password, UserType, Id)"
				+ " VALUES(?, ?, ?, ?);"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, location.getZipCode());
		pstm.setString(2, location.getCity());
		pstm.setString(3, location.getState());
		pstm.setInt		(4, employee.getSSN());
		pstm.setString	(5, employee.getLastName());
		pstm.setString	(6, employee.getFirstName());
		pstm.setString	(7, employee.getAddress());
		pstm.setInt		(8, employee.getZipCode());
		pstm.setString	(9, employee.getTelephone());
		pstm.setInt		(10, employee.getId());
		pstm.setInt		(11, employee.getSSN());
		pstm.setDate	(12, employee.getStartDate());
		pstm.setFloat	(13, employee.getHourlyRate());
		pstm.setString(14, user.getUserName());
		pstm.setString(15, user.getPassword());
		pstm.setString(16, user.getUserType());
		pstm.setInt(17, user.getId());
		
		pstm.execute();
		conn.commit();
	}

	public static Employee findEmployee(Connection conn, int id) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT E.* "
				+ " FROM Employee E "
				+ " WHERE E.Id = ?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		pstm.execute();
		pstm.getMoreResults();
		
		ResultSet rs = pstm.getResultSet();
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT P.*"
				+ " FROM Person P"
				+ " WHERE P.SSN = ?;"
				+ " COMMIT;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setInt(1, PSSN);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " INSERT IGNORE INTO Location(ZipCode, City, State)"
				+ " VALUES(?, ?, ?);"
				+ " UPDATE EmployeeInfo "
				+ " SET LastName=?, FirstName=?, Address=?, ZipCode=?, Telephone=? "
				+ " WHERE Id=?;"
				+ " UPDATE EmployeeInfo"
				+ " SET HourlyRate = ?"
				+ " WHERE Id = ?;"
				+ " COMMIT;";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt		(1, location.getZipCode());
		pstm.setString	(2, location.getCity());
		pstm.setString	(3, location.getState());
		pstm.setString	(4, employee.getLastName());
		pstm.setString	(5, employee.getFirstName());
		pstm.setString	(6, employee.getAddress());
		pstm.setInt		(7, employee.getZipCode());
		pstm.setString	(8, employee.getTelephone());
		pstm.setInt		(9, employee.getId());
		pstm.setInt		(10, employee.getHourlyRate());
		pstm.setInt		(11, employee.getId());
		pstm.execute();
		conn.commit();
	}
	public static List<Stock> getStockList(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
			 	+ " SELECT * FROM Stock;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareCall(sql);
		sql = "SELECT * FROM Stock";
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
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
			conn.commit();
			return list;
	}
	public static Stock findStock(Connection conn, String stockSymbol) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT *"
				+ " FROM Stock"
				+ " WHERE StockSymbol = ?;"
				+ " COMMIT";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " UPDATE Stock"
				+ " SET pricePerShare = ?"
				+ " WHERE stockSymbol = ?"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setFloat	(1, stock.getPricePerShare());
		pstm.setString	(2, stock.getStockSymbol());
		pstm.execute();
		conn.commit();
	}
	
	@SuppressWarnings("deprecation")
	public static boolean deleteEmployee(Connection conn, int brokerId, int SSN) throws SQLException {
		Date dateNow = new Date(System.currentTimeMillis());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		boolean backedUp = false;
		try {
			backedUp = MySQLConnUtils.backupDatabase("C:\\Users\\Work\\Project3Backup" 
					+ dateNow.toString() + "--" 
					+ now.getHours() + "-" + now.getMinutes() + "-" + now.getSeconds() +  ".sql");
		} catch (ClassNotFoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(!backedUp)
			return false;
		
		conn.setAutoCommit(false);
		
		String sql="START TRANSACTION;"
				+ " DELETE FROM Trade"
				+ " WHERE BrokerId = ?;"
				+ " DELETE FROM Transaction"
				+ " WHERE Id NOT IN"
				+ "				(SELECT DISTINCT TransactionId"
				+ "				FROM Trade);"
				+ " DELETE FROM TrailHistory"
				+ " WHERE OrderId NOT IN"
				+ "				(SELECT DISTINCT OrderId"
				+ "				FROM Trade);"
				+ " DELETE FROM Orders"
				+ " WHERE Id NOT IN"
				+ "				(SELECT DISTINCT OrderId"
				+ "				FROM Trade);"
				+ " DELETE FROM Employee"
				+ " WHERE Id=?;"
				+ " DELETE FROM Person "
				+ " WHERE SSN=?;"
				+ " DELETE FROM UserAccount"
				+ " WHERE Id=?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, brokerId); // Delete all trades with brokerId
		pstm.setInt(2, brokerId); // delete employee
		pstm.setInt(3, SSN);//	delete person
		pstm.setInt(4, brokerId);
		pstm.execute();
		conn.commit();
		return true;
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
		conn.setAutoCommit(false);
		String sql="START TRANSACTION;";
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
		conn.commit();
		return list;
	}
	
	public static List<String> getStockTypes(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT DISTINCT Type "
				+ " FROM Stock;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		List<String> list = new ArrayList<String>();
		while(rs.next()){
			list.add(rs.getString("Type"));
		}
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT P.LastName, P.FirstName, C.Id,"
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
				+ " LIMIT 1;"
				+ " COMMIT;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		HighRoller hr = null;
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT P.LastName, P.FirstName, E.Id,"
				+ " (SUM(Trns.PricePerShare)*SUM(O.NumShares)) AS Revenue"
				+ " FROM Employee E, Orders O, Person P, Trade Trd, Transaction Trns"
				+ " WHERE E.SSN = P.SSN"
				+ " AND Trd.OrderId = O.Id"
				+ " AND Trd.BrokerId = E.Id"
				+ " AND	Trd.TransactionId = Trns.Id"
				+ " AND Trns.DateTime IS NOT NULL"
				+ " GROUP BY E.Id"
				+ " ORDER BY Revenue DESC"
				+ " LIMIT 1;"
				+ " COMMIT;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		HighRoller hr = null;
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT *"
				+ " FROM Location L"
				+ " WHERE L.ZipCode = ?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, zipCode);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " INSERT INTO Stock(StockSymbol, CompanyName, Type, PricePerShare, NumShares)"
				+ " VALUES(?, ?, ?, ?, ?);"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, stock.getStockSymbol());
		pstm.setString(2, stock.getCompanyName());
		pstm.setString(3, stock.getType());
		pstm.setFloat(4, stock.getPricePerShare());
		pstm.setInt(5, stock.getNumShares());
		pstm.execute();
		conn.commit();
	}

	public static void editStockName(Connection conn, Stock stock) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " UPDATE Stock"
				+ " SET CompanyName = ?, TYPE = ?"
				+ " WHERE StockSymbol = ?;"
				+ " COMMIT";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stock.getCompanyName());
		pstm.setString(2, stock.getType());
		pstm.setString(3, stock.getStockSymbol());
		pstm.execute();
		conn.commit();
	}
	public static List<Stock> getMostActivelyTradedStock(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ "SELECT COUNT(*) AS TimesTrades, S.*"
				+ " FROM Stock S, Trade Trd"
				+ " WHERE S.StockSymbol = Trd.StockId"
				+ " GROUPS BY S.StockSymbol"
				+ " ORDER BY TimesTraded DESC"
				+ " LIMIT BY 20"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
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
		conn.commit();
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
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " UPDATE Stock"
				+ " SET PricePerShare = ?, NumShares = ?"
				+ " WHERE StockSymbol = ?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setFloat	(1, stock.getPricePerShare());
		pstm.setInt		(2, stock.getNumShares());
		pstm.setString	(3, stock.getStockSymbol());
		pstm.execute();
		conn.commit();
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
		conn.setAutoCommit(false);
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
		conn.commit();
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

	public static boolean hasClients(Connection conn, int employeeId) throws SQLException {
		conn.setAutoCommit(false);
		String sql= "START TRANSACTION;"
				+ "	SELECT *"
				+ "	FROM Client"
				+ " WHERE BrokerId = ?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, employeeId);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		conn.commit();
		if(rs.next())
			return true;
		else
			return false;
	}
		

}

