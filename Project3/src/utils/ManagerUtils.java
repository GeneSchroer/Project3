
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
import conn.ConnectionUtils;
import conn.MySQLConnUtils;
public class ManagerUtils {

	/* Return a list of all Employees in the database*/
	public static List<Employee> getEmployeeList(Connection conn) throws SQLException{
		
		/* Turn off auto commit */
		conn.setAutoCommit(false);

		
		/* Set up statement */
		String sql = "START TRANSACTION;"
				+ "	SELECT E.*,P.*"
				+ " FROM Employee E, Person P "
				+ "	WHERE P.SSN=E.SSN"
				+ " ORDER BY P.LastName ASC;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);

		
		/* Execute and retrieve result set */
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		
		/* Commit to reestablish autocommit mode */
		conn.commit();
		
		/* Create list and add members to it from result set */
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
		/* Commit to re-establish autocommit mode*/
		
		return list;
	}
	
	/* Add a new employee to the database*/
	public static void addEmployee(Connection conn, Employee employee, Location location, UserAccount user) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
				/* It is possible two people live in the same city, 
				 * So ignore any duplicate insertion into location*/
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
		/* Set location */
		pstm.setInt		(1, location.getZipCode());
		pstm.setString	(2, location.getCity());
		pstm.setString	(3, location.getState());
		/* Set Person */
		pstm.setInt		(4, employee.getSSN());
		pstm.setString	(5, employee.getLastName());
		pstm.setString	(6, employee.getFirstName());
		pstm.setString	(7, employee.getAddress());
		pstm.setInt		(8, employee.getZipCode());
		pstm.setString	(9, employee.getTelephone());
		/* Set Employee */
		pstm.setInt		(10, employee.getId());
		pstm.setInt		(11, employee.getSSN());
		pstm.setDate	(12, employee.getStartDate());
		pstm.setFloat	(13, employee.getHourlyRate());
		/* Set UserAccount*/
		pstm.setString(14, user.getUserName());
		pstm.setString(15, user.getPassword());
		pstm.setString(16, user.getUserType());
		pstm.setInt(17, user.getId());
		/* Commit to reestablish autocommit mode */
		pstm.execute();
		conn.commit();
	}

	/* Find an employee by their id */
	public static Employee findEmployee(Connection conn, int id) throws SQLException {
		
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
				+ " SELECT E.* "
				+ " FROM Employee E "
				+ " WHERE E.Id = ?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		
		/* Commit to reestablish autocommit mode */
		pstm.execute();
		conn.commit();

		/* Check result set and return the result */
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
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
	
	/* Find a person by their SSN*/
	public static Person findPerson(Connection conn, int PSSN) throws SQLException {
		
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
		/* Check result set and return the result */
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
	/* Update an employee's information */
	public static void updateEmployee(Connection conn, Employee employee, Location location) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
				/* Insert a new row into location if the employee's new address is new. */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
	}
	
	/* Return a list of all stocks */
	public static List<Stock> getStockList(Connection conn) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
			 	+ " SELECT * FROM Stock;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		/* Create list and add members to it from result set */
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
	/* Find a stock by its stock symbol*/
	public static Stock findStock(Connection conn, String stockSymbol) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
				+ " SELECT *"
				+ " FROM Stock"
				+ " WHERE StockSymbol = ?;"
				+ " COMMIT";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		pstm.execute();
		pstm.getMoreResults();
		/* Check result set and return the result */
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
	/* Update the share price of a stock */
	public static void updateSharePrice(Connection conn, Stock stock) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
				+ " UPDATE Stock"
				+ " SET pricePerShare = ?"
				+ " WHERE stockSymbol = ?"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setFloat	(1, stock.getPricePerShare());
		pstm.setString	(2, stock.getStockSymbol());
		pstm.execute();
		/* Commit to reestablish autocommit mode */
		conn.commit();
	}
	/* Delete an employee from the system*/
	public static boolean deleteEmployee(Connection conn, int brokerId, int SSN) throws SQLException {
		
		/* Back up the database prior to deleting an employee
		 * If the database does not back up, do not delete the employee*/
		boolean backedUp;
		try {
			backedUp = ConnectionUtils.defaultBackup();
		} catch (ClassNotFoundException | IOException | InterruptedException e) {
			backedUp=false;
			e.printStackTrace();
		}
		if(!backedUp)
			return false;
		
		conn.setAutoCommit(false);
		
		String sql="START TRANSACTION;"
				/* Delete all Trade rows with the broker's Id*/
				+ " DELETE FROM Trade"
				+ " WHERE BrokerId = ?;"
				/* Delete all Transaction rows without a corresponding Trade row*/
				+ " DELETE FROM Transaction"
				+ " WHERE Id NOT IN"
				+ "				(SELECT DISTINCT TransactionId"
				+ "				FROM Trade);"
				/* Delete all TrailHistory rows without a corresponding Trade row*/
				+ " DELETE FROM TrailHistory"
				+ " WHERE OrderId NOT IN"
				+ "				(SELECT DISTINCT OrderId"
				+ "				FROM Trade);"
				/* Delete all Orders rows without a corresponding Trade row*/
				+ " DELETE FROM Orders"
				+ " WHERE Id NOT IN"
				+ "				(SELECT DISTINCT OrderId"
				+ "				FROM Trade);"
				/* Delete the person and their account information*/
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
	/* Return the sales report for a particular month and year*/
	public static List<SalesReport> getSalesReport(Connection conn, String year, String month) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Create list and add members to it from result set */
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
		
		/* Create list and add members to it from result set */
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
	/* Return a list of stock types that are available*/
	public static List<String> getStockTypes(Connection conn) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
				+ " SELECT DISTINCT Type "
				+ " FROM Stock;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		/* Create list and add members to it from result set */
		List<String> list = new ArrayList<String>();
		while(rs.next()){
			list.add(rs.getString("Type"));
		}
		/* Create list and add members to it from result set */
		conn.commit();
		return list;
	}
	
	/* Return information on the Customer that has generated the most revenue*/
	public static HighRoller findCustomerWithMostRevenue(Connection conn) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Check result set and return the result */
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
	/* Find the customer representative that has generated the most revenue*/
	public static HighRoller findRepresentativeWithMostRevenue(Connection conn) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Check result set and return the result */
		ResultSet rs = pstm.getResultSet();
		HighRoller hr = null;
		/* Commit to reestablish autocommit mode */
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
	/* Return a Location row based on its zip code*/
	public static Location findLocation(Connection conn, Integer zipCode) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
		/* Check result set and return the result */
		if(rs.next()){
			Location location = new Location(
								rs.getInt("ZipCode"),
								rs.getString("City"),
								rs.getString("State"));
			return location;
		}
		return null;
	}
	/* Add a stock into the market*/
	public static void addStock(Connection conn, Stock stock) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
	}
	/* Edit the name and type of a stock*/
	public static void editStockName(Connection conn, Stock stock) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
	}
	/* Return a list of the most actively traded stocks*/
	public static List<Stock> getMostActivelyTradedStock(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT S.*, COUNT(*) AS TimesTraded "
				+ " FROM Stock S, Trade Trd"
				+ " WHERE S.StockSymbol = Trd.StockId"
				+ " GROUP BY Trd.StockId"
				+ " ORDER BY TimesTraded DESC"
				+ " LIMIT 5;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		/* Create list and add members to it from result set */
		ResultSet rs = pstm.getResultSet();
		List<Stock> list = new ArrayList<Stock>();
		while(rs.next()){
			Stock stock = new Stock(
							rs.getString("StockSymbol"),
							rs.getString("CompanyName"),
							rs.getString("Type"),
							rs.getFloat("PricePerShare"),
							rs.getInt("TimesTraded")
							);
			list.add(stock);
		}
		/* Commit to reestablish autocommit mode */
		conn.commit();
		return list;
	}
	/* Return the biggest employee Id in the system. Used for adding a new employee to the system*/
	public static int getMostRecentEmployeeId(Connection conn) throws SQLException{
		/* Set up statement */
		String sql = "SELECT Id FROM Employee ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		/* Check result set and return the result */
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("Id");
		else 
			return 0;
	}
	/*Update the share price and number of shares available for a stock*/
	public static void updateStock(Connection conn, Stock stock) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
	}
	/* Get the summary listing of a particular stock */
	public static SummaryListing getSummaryListingByStockSymbol(Connection conn, String stockSymbol) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
		/* Check result set and return the result */
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
	/* Return the summary listing of a particular type of stock*/
	public static SummaryListing getSummaryListingByStockType(Connection conn, String type) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Check result set and return the result */
		ResultSet rs = pstm.getResultSet();
		conn.commit();
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

	/* Return the summary listing of a particular customer*/
	public static SummaryListing getSummaryListingByCustomerName(Connection conn, String firstName, String lastName) throws SQLException{
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
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
		/* Commit to reestablish autocommit mode */
		conn.commit();
		/* Check result set and return the result */
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
	/* Return whether or not an employee has any clients*/
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
	/* move a client to another employee*/
	public static boolean moveAccount(Connection conn, int clientId, int brokerId) throws SQLException {
		/* Turn off auto commit so a transaction may be executed*/
		conn.setAutoCommit(false);
		/* Set up statement */
		String sql = "START TRANSACTION;"
				+ "	UPDATE Client"
				+ " SET BrokerId = ?"
				+ " WHERE Id = ?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, brokerId);
		pstm.setInt(2, clientId);
		pstm.execute();
		/* Commit to reestablish autocommit mode */
		conn.commit();
		/* Return true if successful*/
		return true;
	}
		

}

