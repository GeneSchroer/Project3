package utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import beans.Account;
import beans.AccountDetails;
import beans.Client;
import beans.ClientInfo;
import beans.Location;
import beans.MailingList;
import beans.OpenOrder;
import beans.Person;
import beans.Stock;
import beans.UserAccount;

public class RepresentativeUtils {

	public static List<Client> getClientList(Connection conn, int brokerId) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
					+ " SELECT C.*, P.*"
					+ " FROM Client C, Person P"
					+ " WHERE C.id=P.SSN AND C.BrokerId=?;"
					+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, brokerId);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		List<Client> list = new ArrayList<Client>();
		while(rs.next()){
			Client client = new Client(rs.getInt("SSN"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("address"), rs.getInt("zipCode"), rs.getString("telephone"),
					rs.getString("email"), rs.getInt("rating"), rs.getString("creditCardNumber"), rs.getInt("BrokerId"));
			list.add(client);
		}
		conn.commit();
		return list;
	}

	public static void addClient(Connection conn, Client client, Location location, UserAccount user) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
					+ " INSERT IGNORE Location(ZipCode, City, State)"
					+ " VALUES(?, ?, ?);"
					+ " INSERT INTO Person(SSN, LastName, FirstName, Address, ZipCode, Telephone)"
					+ " VALUES(?, ?, ?, ?, ?, ?);"
					+ " INSERT INTO Client(Email, Rating, CreditCardNumber, Id, BrokerId)"
					+ " VALUES(?, ?, ?, ?, ?);"
					+ " INSERT INTO UserAccount(UserName, Password, UserType, Id)"
					+ " VALUES(?, ?, ?, ?);"
					+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		// Insert into Location
		
		// Set Location(ZipCode)
		pstm.setInt(1, location.getZipCode());
		// Set Location(City)
		pstm.setString(2, location.getCity());
		// Set Location(State)
		pstm.setString(3, location.getState());
		
		// Insert into Person
		
		// Set Person(SSN)
		pstm.setInt		(4, client.getSSN());
		// Set Person(LastName)
		pstm.setString	(5, client.getLastName());
		// Set Person(FirstName)
		pstm.setString	(6, client.getFirstName());
		// Set Person(Address)
		pstm.setString	(7, client.getAddress());
		// Set Person(ZipCode)
		pstm.setInt		(8, client.getZipCode());
		// Set Person(Telephone)
		pstm.setString	(9, client.getTelephone());
		
		// Insert into Client
		// Set Client(Email)
		pstm.setString(10, client.getEmail());
		// Set Client(Rating)
		pstm.setInt(11, client.getRating());
		// Set Client(CreditCardNumber)
		pstm.setString(12, client.getCreditCardNumber());
		// Set Client(Id)
		pstm.setInt(13, client.getId());
		// Set Client(BrokerId)
		pstm.setInt(14, client.getBrokerId());
		
		//Finally, insert into UserAccount
		// Insert UserAccount(UserName)
		pstm.setString(15, user.getUserName());
		// Insert UserAccount(Password)
		pstm.setString(16, user.getPassword());
		// Insert UserAccount(UserType)
		pstm.setString(17, user.getUserType());
		// Insert UserAccount(Id)
		pstm.setInt(18, user.getId());
		pstm.execute();
		conn.commit();
	}

	public static Client findClient(Connection conn, int id) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT * FROM Client WHERE Id = ?;"
				+ " COMMIT";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		
		conn.commit();
		if(rs.next()){
		
			Person person 			= findPerson(conn, rs.getInt("Id"));
			String lastName 		= person.getLastName();
			String firstName 		= person.getFirstName();
			String address 			= person.getAddress();
			int zipCode 			= person.getZipCode();
			String telephone 		= person.getTelephone();
			Client client 			= new Client(rs.getInt("Id"), 
												lastName,
												firstName, 
												address, 
												zipCode, 
												telephone, 
												rs.getString("Email"), 
												rs.getInt("Rating"), 
												rs.getString("CreditCardNumber"),
												rs.getInt("BrokerId"));
			return client;
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
				+ " COMMIT";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, PSSN);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		conn.commit();
		if(rs.next()){
			Person person = new Person(rs.getInt("SSN"),
								rs.getString("LastName"),
								rs.getString("FirstName"),
								rs.getString("Address"),
								rs.getInt("ZipCode"),
								rs.getString("Telephone"));
			return person;
		}
		else		
			return null;
		}

	public static void updateClient(Connection conn, Client client, Location location) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " INSERT IGNORE INTO Location(ZipCode, City, State)"
				+ " VALUES(?, ?, ?);"
				+ " UPDATE Person SET LastName = ?, FirstName = ?, Address = ?, ZipCode = ?,Telephone = ?"
				+ " WHERE SSN = ?;"
				+ " UPDATE Client SET Email = ?, Rating = ?, CreditCardNumber = ?"
				+ " WHERE Id = ?;"
				+ " COMMIT";

		PreparedStatement pstm = conn.prepareStatement(sql);
		// Set Location(ZipCode)
		pstm.setInt(1, location.getZipCode());
		// Set Location (City)
		pstm.setString(2, location.getCity());
		// Set Location(State)
		pstm.setString(3, location.getState());
		
		// Set Person information
		// Set Person(LastName)
		pstm.setString	(4, client.getLastName());
		// Set Person(FirstName)
		pstm.setString	(5, client.getFirstName());
		// Set Person(Address)
		pstm.setString	(6, client.getAddress());
		// Set Person(ZipCode)
		pstm.setInt		(7, client.getZipCode());
		// Set Person(Telephone)
		pstm.setString	(8, client.getTelephone());
		// Set Person(SSN) 
		pstm.setInt		(9, client.getId());
		
		//Update Client info
		// Set Client(Email)
		pstm.setString	(10, client.getEmail());
		// Set Client(Rating)
		pstm.setInt		(11, client.getRating());
		// Set Client(CreditCardNumber)
		pstm.setString	(12, client.getCreditCardNumber());
		// Set Client(Id)
		pstm.setInt		(13, client.getId());
		
		pstm.execute();
		conn.commit();
	}
	
	private static Integer getMostRecentOrderId(Connection conn) throws SQLException{
		
		String sql = "SELECT Id FROM Orders ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("id");
		else 
			return null;
	}
private static Integer getMostRecentTransactionId(Connection conn) throws SQLException{
		
		String sql = "SELECT Id FROM Transaction ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("Id");
		else 
			return 0;
	}
	public static boolean deleteClient(Connection conn, int clientId) throws SQLException {
		//This is a work in progress.
		
		// What will likely happen is the foreign keys constraints
		// For a bunch of tables will temporarily be switched
		// to ON DELETE CASCADE
		// Then it is a simple matter to delete 
		// The Client values in the Person table, 
		// which will cause a chain reaction on each table
		
		conn.setAutoCommit(false);
		
		String sql = "START TRANSACTION;"
				+ " SELECT Id "
				+ " FROM Account"
				+ " WHERE Client=?;"
				+ "COMMIT;";
				
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setInt(1, clientId);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		List<Integer> accountList=new ArrayList<Integer>();
		while(rs.next()){
			accountList.add(rs.getInt("Id"));
		}
		
		for(int i=0;i<accountList.size(); ++i){
			int accountId=accountList.get(i);
			sql = "START TRANSACTION;"
				+ " DELETE FROM Trade"
				+ " WHERE AccountId=?;"
				+ " DELETE FROM Transaction"
				+ " WHERE Id NOT IN"
				+ "				(SELECT DISTINCT TransactionId"
				+ "				 FROM Trade);"
				+ " DELETE FROM TrailHistory"
				+ " WHERE OrderId NOT IN"
				+ "				(SELECT DISTINCT OrderId"
				+ "				FROM Trade);"
				+ " DELETE FROM Orders"
				+ " WHERE Id NOT IN"
				+ "				(SELECT DISTINCT OrderId"
				+ "				FROM Trade);"
				+ " DELETE FROM HasStock"
				+ " WHERE AccountId=?;"
				+ " DELETE FROM Account"
				+ " WHERE Id = ?;"
				+ " DELETE FROM UserAccount"
				+ " WHERE Id = ?;"
				+ " COMMIT";
		
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, accountId);
			pstm.setInt(2, accountId);
			pstm.setInt(3, accountId);
			pstm.setInt(4, clientId);
			pstm.execute();
				}
		sql= "START TRANSACTION;"
				+ "DELETE FROM Client"
				+  " WHERE ID=?;"
				+ "DELETE FROM Person"
				+ " WHERE SSN=?;"
				+ "DELETE FROM UserAccount"
				+ " WHERE Id=?;"
				+ "COMMIT;";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		pstm.setInt(2, clientId);
		pstm.setInt(3, clientId);
		pstm.execute();
		conn.commit();
		return true;
	}
	public static List<ClientInfo> getMailingList(Connection conn, int brokerId) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT *"
				+ " FROM ClientInfo"
				+ " WHERE BrokerId = ?"
				+ " ORDER BY LastName ASC;"
				+ " COMMIT;";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setInt(1, brokerId);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		List<ClientInfo> list = new ArrayList<ClientInfo>();
		while(rs.next()){
			ClientInfo client= new ClientInfo(
									rs.getInt("ClientId"), 
									rs.getString("FirstName"), 
									rs.getString("LastName"), 
									rs.getString("Address"), 
									rs.getInt("ZipCode"), 
									rs.getString("Telephone"),
									rs.getString("Email"),
									rs.getInt("Rating"),
									rs.getString("CreditCardNumber"),
									rs.getInt("ClientId"),
									rs.getString("City"),
									rs.getString("State"));
			list.add(client);
		}
		conn.commit();
		return list;
		}
	public static Stock getStock(Connection conn, String inputStockSymbol) throws SQLException{
		conn.setAutoCommit(false);
		
		String sql = "START TRANSACTION;"
				+ " SELECT PricePerShare"
				+ " FROM Stock"
				+ " WHERE StockSymbol = ?;"
				+ " COMMIT;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, inputStockSymbol);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		conn.commit();
		if(rs.next()){
			Stock stock = new Stock(rs.getString("stockSymbol"), rs.getString("companyName"), rs.getString("type"), rs.getFloat("pricePerShare"));
			return stock;
		}
		else{
			return null;
		}
	}
	public static List<Stock> getStockList(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT *"
				+ " FROM Stock;"
				+ " COMMIT";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		
		List<Stock> list = new ArrayList<Stock>();
		while(rs.next()){
			String stockSymbol = rs.getString("stockSymbol");
			String companyName = rs.getString("companyName");
			String type = rs.getString("type");
			float pricePerShare = rs.getFloat("pricePerShare");
			Stock stock = new Stock(stockSymbol, companyName, type, pricePerShare);
			list.add(stock);
			
		}
		conn.commit();
		return list;
	}

	public static void recordOrder(Connection conn, String stockSymbol, String orderType, String priceType, Timestamp dateTime,
			Integer numSharesParsed, Double percentageParsed, Float pricePerShareParsed, int accountId, int brokerId) throws SQLException {
		conn.setAutoCommit(false);
		float stockPrice=0;
		float finalPricePerShare=0;
		//If this is a trailing stop, we need to determine the current Price Per Share of the stock is
		// In order to detertmine what the stop price and percentage to stop is;
		
		String sql = "START TRANSACTION;"
				+ " SELECT PricePerShare"
				+ " FROM STOCK"
				+ " WHERE StockSymbol=?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		rs.next();
		stockPrice = rs.getFloat("PricePerShare");
		finalPricePerShare = rs.getFloat("PricePerShare");
		
		sql = "START TRANSACTION;"
				+ " INSERT INTO Orders (NumShares, PricePerShare, Id, DateTime, Percentage, PriceType, OrderType)"
				+ " VALUES(?, ? ,?, ?, ?, ?, ?);"
				+ " INSERT INTO Transaction(Id, Fee, DateTime, PricePerShare)"
				+ " VALUES(?, ?, ?, ?);"
				+ " INSERT INTO Trade(AccountId, BrokerId, OrderId, TransactionId, StockId)"
				+ " VALUES(?, ?, ? ,? ,?);"
				+ " COMMIT;";
		pstm = conn.prepareStatement(sql);
		
		//Set Order(NumShares)
		pstm.setInt(1, numSharesParsed);
		
		//Set Order(Id)
		// Order Id is simply a number, so get the largest Order(Id)
		// and add one
		int orderId = getMostRecentOrderId(conn)+1;
		pstm.setInt(3, orderId);
		
		//Set Order(DateTime)
		pstm.setTimestamp(4, dateTime);
		
		//Set Order(PriceType)
		pstm.setString(6, priceType);
		
		//Set Order(OrderType)
		pstm.setString(7, orderType);
		
		//Set Order(PricePerShare) and Order(Percentage)
		//Setting these values depends on the Price type
		if(priceType.equals("Trailing Stop")){
			double x = calculateStopPriceOrPercentage(stockPrice, pricePerShareParsed, percentageParsed);
		
			if(pricePerShareParsed==null){
				// calculate the Price Per Share to stop at
				pstm.setFloat(2, (float)x);
				pstm.setDouble(5, percentageParsed);	}
			else if(percentageParsed==null){
				// calculate the Percentage to stop at
				pstm.setFloat(2, pricePerShareParsed);
				pstm.setDouble(5, x);	}
			else
				throw new SQLException();	}
		//if the Price Type is a Hidden Stop, we only need to worry about the Price Per Share
		else if(priceType.equals("Hidden Stop")){
			pstm.setFloat(2, pricePerShareParsed);
			pstm.setNull(5, Types.DOUBLE);	}
		//if the Price Type is Market or Market On Close, both Price Per Share and Percentage are Null
		else{
			pstm.setNull(2, Types.FLOAT);
			pstm.setNull(5, Types.DOUBLE);	}
			
		//Set Transaction values
		
		//Set Transaction(Id)
		int transactionId = getMostRecentTransactionId(conn)+1;
		pstm.setInt(8, transactionId);
		
		//Set Transaction(Fee, DateTime, and PricePerShare)
		//If the order is a Market or Market on Close order, set the values right now
		if(priceType.equals("Market") || priceType.equals("Market On Close")){
			float fee = (float)(finalPricePerShare * 0.05 * numSharesParsed);
			pstm.setFloat(9, fee);
			pstm.setTimestamp(10, dateTime);
			pstm.setFloat(11, finalPricePerShare);	}
		//Otherwise set the values to null
		else{
			pstm.setNull(9, Types.FLOAT);
			pstm.setNull(10, Types.TIMESTAMP);
			pstm.setNull(11, Types.FLOAT);	}
		
		//Set Trade values
		
		//Set Trade(AccountId)
		pstm.setInt(12, accountId);
		
		//Set Trade(BrokerId)
		pstm.setInt(13, brokerId);
		
		//Set Trade(OrderId)
		pstm.setInt(14, orderId);
		
		//Set Trade(TransactionId)
		pstm.setInt(15, transactionId);
		
		//Set Trade(StockId)
		pstm.setString(16, stockSymbol);
		
		pstm.execute();
		
		conn.commit();
	}
	private static double calculateStopPriceOrPercentage(Float pricePerShare, Float stopPrice, Double percentage){
			//return Stop Price
			if(stopPrice == null || stopPrice==0)
				return pricePerShare * (percentage/100);
			//return Percentage
			else if(percentage == null || percentage==0)
				return stopPrice/pricePerShare*100;
			else 
				return 0;
	}

	public static void completeOrder(Connection conn, String stockSymbol, String orderType, String priceType, Timestamp timestamp,
			int numSharesParsed, double percentageParsed, int accountId, int brokerId) throws SQLException {
	
		String sql = "INSERT INTO Trade(AccountId, BrokerId, TransactionId, OrderId, StockId)"
				+" VALUES(?, ?, null, ?, ?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, accountId);
		pstm.setInt(2, brokerId);
		//pstm.setInt(3, orderId);
		pstm.setString(4, stockSymbol);
		pstm.executeUpdate();
	}
	public static List<OpenOrder> getOpenOrderList(Connection conn, int brokerId) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT O.*, Trd.AccountId AS AccountId, S.StockSymbol, S.CompanyName"
				+ " FROM Orders O, Trade Trd, Transaction Trns, Stock S"
				+ " WHERE Trd.OrderId = O.Id AND Trd.StockId=S.StockSymbol AND Trd.TransactionId=Trns.Id AND Trns.DateTime IS NULL;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		
		List<OpenOrder> list = new ArrayList<OpenOrder>();
		while(rs.next()){
			Integer numShares = rs.getInt("NumShares");
			Float pricePerShare = rs.getFloat("PricePerShare");
			int orderId = rs.getInt("Id");
			Timestamp dateTime = rs.getTimestamp("DateTime");
			Double percentage = rs.getDouble("Percentage");
			String priceType = rs.getString("PriceType");
			String orderType = rs.getString("OrderType");
			int accountId = rs.getInt("AccountId");
			String stockSymbol = rs.getString("StockSymbol");
			String companyName = rs.getString("CompanyName");
			OpenOrder openOrder = new OpenOrder(numShares, pricePerShare, orderId, dateTime, percentage, priceType, orderType, accountId, stockSymbol, companyName);
			list.add(openOrder);
		}
		conn.commit();
		return list;
	}
	public static List<Stock> getStockSuggestionList(Connection conn, int customerId) throws SQLException{
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
				+ " SELECT * FROM Stock"
				+ " WHERE Type IN"
				+ " (SELECT DISTINCT S.Type FROM Account A, Client C, Orders O, Stock S, Trade Trd"
				+ " WHERE C.Id = ?"
				+ " AND A.Client = C.Id"
				+ " AND Trd.AccountId = A.Id"
				+ " AND Trd.OrderId = O.Id"
				+ " AND S.StockSymbol = Trd.StockId"
				+ " GROUP BY S.Type)"
				+ " LIMIT 20;"
				+ " COMMIT";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, customerId);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		
		List<Stock> list = new ArrayList<Stock>();
		while(rs.next()){
			Stock stock = new Stock(
							rs.getString("stockSymbol"),
							rs.getString("companyName"),
							rs.getString("type"),
							rs.getFloat("pricePerShare"));
			list.add(stock);
		}
		conn.commit();
		return list;
		
	}

	public static List<Account> getAccountList(Connection conn, int clientId) throws SQLException {
		String sql="SELECT * FROM Account WHERE Client = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		ResultSet rs = pstm.executeQuery();
		List<Account> list = new ArrayList<Account>(); 
		while(rs.next()){
			Account account = new Account(
									rs.getInt("Id"),
									rs.getDate("DateOpened"),
									rs.getInt("Client"));
			list.add(account);
		}
		
		return list;
	}

	public static void addAccount(Connection conn, Date dateOpenedParsed, int clientId) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		
		sql = "INSERT INTO Account(Id, DateOpened, Client)"
				+ " VALUES(?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		
		//Get most recent accountId
		
		int accountId = getMostRecentAccountId(conn) + 1;
		pstm.setInt(1, accountId);
		pstm.setDate(2, dateOpenedParsed);
		pstm.setInt(3, clientId);
		pstm.executeUpdate();
		
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
	}
	
	private static int getMostRecentAccountId(Connection conn) throws SQLException{
		String sql = "SELECT Id From Account ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("Id");
		else
			return 0;
	}

	public static List<AccountDetails> getAccountDetails(Connection conn, int brokerId) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION;"
					+ "	SELECT 	A.* "
					+ " FROM 	AccountDetails A, Client C"
					+ " WHERE 	A.Client = C.Id"
					+ " AND 	C.BrokerId = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, brokerId);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		List<AccountDetails> list = new ArrayList<AccountDetails>();
		while(rs.next()){
			AccountDetails account = new AccountDetails(
										rs.getInt("Id"),
										rs.getDate("DateOpened"),
										rs.getInt("Client"),
										rs.getString("LastName"),
										rs.getString("FirstName"),
										rs.getString("Address"),
										rs.getInt("ZipCode"),
										rs.getString("Telephone"));
			list.add(account);
		}
		conn.commit();
		return list;
	}
	
}

