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
import beans.Client;
import beans.Location;
import beans.MailingList;
import beans.OpenOrder;
import beans.Person;
import beans.Stock;

public class RepresentativeUtils {

	public static List<Client> getClientList(Connection conn) throws SQLException {
		String sql = "select C.*, P.* from Client C, Person P where C.id=P.SSN";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		List<Client> list = new ArrayList<Client>();
		while(rs.next()){
			Client client = new Client(rs.getInt("SSN"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("address"), rs.getInt("zipCode"), rs.getString("telephone"),
					rs.getString("email"), rs.getInt("rating"), rs.getString("creditCardNumber"));
			list.add(client);
		}
		return list;
	}

	public static void addClient(Connection conn, Client client, Location location) throws SQLException {
		String sql = "INSERT IGNORE Location(ZipCode, City, State)"
				+ " VALUES(?, ?, ?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, location.getZipCode());
		pstm.setString(2, location.getCity());
		pstm.setString(3, location.getState());
		pstm.executeUpdate();
		
		
		 sql = "insert into Person(SSN, LastName, FirstName, Address, ZipCode, Telephone)"
				+" values(?, ?, ?, ?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		
		pstm.setInt		(1, client.getSSN());
		pstm.setString	(2, client.getLastName());
		pstm.setString	(3, client.getFirstName());
		pstm.setString	(4, client.getAddress());
		pstm.setInt		(5, client.getZipCode());
		pstm.setString	(6, client.getTelephone());
		pstm.executeUpdate();
		
		sql = "insert into Client(Email, Rating, CreditCardNumber, ID)"
				+" value(?, ?, ?, ?)";
		
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, client.getEmail());
		pstm.setInt(2, client.getRating());
		pstm.setString(3, client.getCreditCardNumber());
		pstm.setInt(4, client.getId());
		pstm.executeUpdate();
	}

	public static Client findClient(Connection conn, int id) throws SQLException {
		String sql = "select * from Client where id = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()){
			String email 			= rs.getString("email");
			int rating 				= rs.getInt("rating");
			String creditCardNumber = rs.getString("creditCardNumber");
			int clientId			= rs.getInt("id");
		
			Person person 			= findPerson(conn, clientId);
			String lastName 		= person.getLastName();
			String firstName 		= person.getFirstName();
			String address 			= person.getAddress();
			int zipCode 			= person.getZipCode();
			String telephone 		= person.getTelephone();
			Client client 			= new Client(clientId, lastName,
					firstName, address, zipCode, telephone, email, rating, creditCardNumber);
			return client;
		}
		else
		
		
		return null;
	}
	public static Person findPerson(Connection conn, int PSSN) throws SQLException {
		String sql = "select P.* from Person P where P.SSN = ?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, PSSN);
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()){
				int SSN = rs.getInt("SSN");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				String address = rs.getString("address");
				int zipCode = rs.getInt("zipCode");
				String telephone = rs.getString("telephone");
				Person person = new Person(SSN, lastName, firstName, address, 
					zipCode, telephone);
				return person;
			}
			else		
				return null;
		}

	public static void updateClient(Connection conn, Client client, Location location) throws SQLException {
		String sql = "INSERT IGNORE INTO Location(ZipCode, City, State)"
				+ "VALUES(?, ?, ?)";
		PreparedStatement pstm = conn.prepareCall(sql);
		pstm.setInt(1, location.getZipCode());
		pstm.setString(2, location.getCity());
		pstm.setString(3, location.getState());
		pstm.executeUpdate();
		
		sql = "update Person set LastName=?, FirstName=?, Address=?, ZipCode=?,"
				+	" Telephone=? where SSN=?";
		pstm = conn.prepareStatement(sql);
			
			pstm.setString	(1, client.getLastName());
			pstm.setString	(2, client.getFirstName());
			pstm.setString	(3, client.getAddress());
			pstm.setInt		(4, client.getZipCode());
			pstm.setString	(5, client.getTelephone());
			pstm.setInt		(6, client.getId());
			pstm.executeUpdate();
			
			sql = "update Client set Email=?, Rating=?, CreditCardNumber=? where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString	(1, client.getEmail());
			pstm.setInt		(2, client.getRating());
			pstm.setString	(3, client.getCreditCardNumber());
			pstm.setInt		(4, client.getId());
			pstm.executeUpdate();
	}
	
	private static Integer getMostRecentOrderId(Connection conn) throws SQLException{
		
		String sql = "Select Id from Orders order by Id desc";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("id");
		else 
			return null;
	}
private static Integer getMostRecentTransactionId(Connection conn) throws SQLException{
		
		String sql = "Select Id from Transaction by Id desc";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("id");
			
		
		else 
			return 0;
	}
	public static void deleteClient(Connection conn, int id) throws SQLException {
		String sql = "delete from Client where id = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		pstm.executeUpdate();
		
		
		sql = "delete from Person where SSN = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, id);
		pstm.executeUpdate();
	}
	public static List<MailingList> getMailingList(Connection conn) throws SQLException{
			String sql = "select concat(P.FirstName, ' ',  P.LastName) as Name, C.Email, P.Address, L.City, L.State, L.ZipCode from Client C, Location L, Person P where P.ZipCode=L.ZipCode and P.SSN=C.Id order by P.LastName asc";
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery();
			List<MailingList> list = new ArrayList<MailingList>();
			while(rs.next()){
				MailingList ms = new MailingList(rs.getString("name"), rs.getString("email"), rs.getString("address"), rs.getString("city"), rs.getString("state"), rs.getInt("zipCode"));
				list.add(ms);
			}
			return list;
		}
	public static Stock getStock(Connection conn, String inputStockSymbol) throws SQLException{
		String sql = "select PricePerShare from Stock where StockSymbol = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, inputStockSymbol);
		ResultSet rs = pstm.executeQuery();
		if(rs.next()){
			Stock stock = new Stock(rs.getString("stockSymbol"), rs.getString("companyName"), rs.getString("type"), rs.getFloat("pricePerShare"));
			return stock;
		}
		else{
			return null;
		}
	}
	public static List<Stock> getStockList(Connection conn) throws SQLException{
		String sql = "select * from Stock";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		
		List<Stock> list = new ArrayList<Stock>();
		while(rs.next()){
			String stockSymbol = rs.getString("stockSymbol");
			String companyName = rs.getString("companyName");
			String type = rs.getString("type");
			float pricePerShare = rs.getFloat("pricePerShare");
			Stock stock = new Stock(stockSymbol, companyName, type, pricePerShare);
			list.add(stock);
			
		}
		return list;
}

	public static void recordOrder(Connection conn, String stockSymbol, String orderType, String priceType, Timestamp timestamp,
			int numSharesParsed, double percentageParsed, int accountId, int brokerId) throws SQLException {
		
		String sql = "SELECT PricePerShare FROM Stock WHERE StockSymbol=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		ResultSet rs = pstm.executeQuery();
		float pricePerShare=0;
		if(rs.next()){
			pricePerShare = rs.getFloat("PricePerShare");
		}
		sql = "INSERT INTO Orders (NumShares, PricePerShare, Id, DateTime, Percentage, PriceType, OrderType)"
				+ " VALUES(?, ? ,?, ?, ?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		if(priceType.equals("Hidden Stop"))
			pstm.setInt(1, numSharesParsed);
		else
			pstm.setNull(1, Types.FLOAT);
		pstm.setFloat(2, pricePerShare);
		
		int orderId = getMostRecentOrderId(conn) + 1;
		
		pstm.setInt(3, orderId);
		System.out.println("good");
		pstm.setTimestamp(4, timestamp);
		if(priceType.equals("Trailing Stop"))
			pstm.setDouble(5, percentageParsed);
		else
			pstm.setNull(5, Types.DOUBLE);;
		pstm.setString(6, priceType);
		pstm.setString(7, orderType);
		pstm.executeUpdate();
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
		String sql = "SELECT O.*, Trd.AccountId AS AccountId, S.StockSymbol, S.CompanyName"
				+ " FROM Orders O, Trade Trd, Transaction Trns, Stock S"
				+ " WHERE Trd.OrderId = O.Id AND Trd.StockId=S.StockSymbol AND Trd.TransactionId=Trns.Id AND Trns.DateTime is null; ";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		
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
		return list;
	}
	public static List<Stock> getStockSuggestionList(Connection conn, int customerId) throws SQLException{
		String sql = "SELECT * FROM Stock"
				+ " WHERE Type = "
				+ "(SELECT S.Type FROM Account A, Client C, Orders O, Stock S, Trade Trd"
				+ " WHERE C.Id = ?"
				+ " AND A.Client = C.Id"
				+ " AND Trd.AccountId = A.Id"
				+ " AND Trd.OrderId = O.Id"
				+ " AND S.StockSymbol = Trd.StockId"
				+ " GROUP BY S.Type)"
				+ " LIMIT 20";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, customerId);
		ResultSet rs = pstm.executeQuery();
		
		List<Stock> list = new ArrayList<Stock>();
		while(rs.next()){
			Stock stock = new Stock(
							rs.getString("stockSymbol"),
							rs.getString("companyName"),
							rs.getString("type"),
							rs.getFloat("pricePerShare"));
			list.add(stock);
		}
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

	public static void addAccount(Connection conn, Account account, int clientId) throws SQLException {
		String sql = "INSERT INTO Account(Id, DateOpened, Client)"
				+ " VALUES(?, ?, ?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, account.getId());
		pstm.setDate(2, account.getDateOpened());
		pstm.setInt(3, account.getClientId());
		pstm.executeUpdate();
	}
	
}

