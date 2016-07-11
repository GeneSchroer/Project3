package utils;
import beans.Transaction;
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
import beans.BestSeller;
import beans.FullOrder;
import beans.HasStock;
import beans.History;
import beans.Orders;
import beans.Portfolio;
import beans.Stock;
import beans.TrailingHistory;
public class CustomerUtils {

	
	
	
	/* Return the customer's stock portfolio*/
	public static List<HasStock> getStockPortfolio(Connection conn, int customerId) throws SQLException{
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql="SELECT H.*, S.PricePerShare"
				+ " FROM Account A, HasStock H, Stock S"
				+ " WHERE A.Client = ?"
				+ " AND A.Id = H.AccountId"
				+ " AND H.StockSymbol = S.StockSymbol"
				+ " ORDER BY AccountId ASC";
	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, customerId);
		/* Create list and add members to it from result set*/
		ResultSet rs = pstm.executeQuery();
		List<HasStock> list = new ArrayList<HasStock>();
		while(rs.next()){
			HasStock hasStock = new HasStock(
									rs.getInt("AccountId"), 
									rs.getString("StockSymbol"), 
									rs.getInt("NumShares"),
									rs.getFloat("PricePerShare"));
			list.add(hasStock);
		}
	
	
	return list;
	}
	
	

	/* Place an order for a stock */
	public static void placeOrder(Connection conn, FullOrder fullOrder) throws SQLException {
		String sql=null;
		PreparedStatement pstm = null;
		conn.setAutoCommit(false);
		float stockPrice=0;
		//Insert into Order Table
		
		//If this is a trailing stop, we need to determine the current Price Per Share of the stock
		// in order to determine what the stop price and percentage to stop is.
		if(fullOrder.getPriceType().equals("Trailing Stop")){
			sql="SELECT PricePerShare FROM STOCK WHERE StockSymbol=?";
		
			pstm=conn.prepareStatement(sql);
			pstm.setString(1, fullOrder.getStockId());
			ResultSet rs = pstm.executeQuery();
			rs.next();
			stockPrice = rs.getFloat("PricePerShare");
		}
		float finalPricePerShare=0;
		if(fullOrder.getPriceType().equals("Market")||fullOrder.getPriceType().equals("Market On Close")){
			sql = "SELECT PricePerShare	FROM Stock"
					+ "	WHERE StockSymbol = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, fullOrder.getStockId());
			ResultSet rs = pstm.executeQuery();
			rs.next();
			finalPricePerShare = rs.getFloat("PricePerShare");
		}
		sql= "START TRANSACTION;"
				+ " INSERT INTO Orders (NumShares, PricePerShare, Id, DateTime, Percentage, PriceType, OrderType)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?);"
				+ " INSERT INTO Transaction(Id, Fee, DateTime, PricePerShare)"
				+ " VALUES(?, ?, ?, ?);"
				+ " INSERT INTO Trade(AccountId, BrokerId, OrderId, TransactionId, StockId)"
				+ " VALUES(?, ?, ?, ?, ?);"
				+ "	COMMIT;";
		
		pstm = conn.prepareStatement(sql);
		
		//Set Order(NumShares)
		pstm.setInt(1, fullOrder.getNumShares());
		
		//if the Price Type is a Trailing Stop, we need to determine the Stop Price and the Percentage.
		if(fullOrder.getPriceType().equals("Trailing Stop")){
			
			double x = calculateStopPriceOrPercentage(stockPrice, fullOrder.getPricePerShare(), fullOrder.getPercentage());

			if(fullOrder.getPricePerShare()==null){
				// calculate the Price Per Share to stop at
				pstm.setFloat(2, (float)x);
				pstm.setDouble(5, fullOrder.getPercentage());
			}
			else if(fullOrder.getPercentage()==null){
				// calculate the Percentage to stop at
				pstm.setFloat(2, fullOrder.getPricePerShare());
				pstm.setDouble(5, x);
			}
			else{
				throw new SQLException();
			}
		}
		//if the Price Type is a Hidden Stop, we only need to worry about the Price Per Share
		else if(fullOrder.getPriceType().equals("Hidden Stop")){
			pstm.setFloat(2, fullOrder.getPricePerShare());
			pstm.setNull(5, Types.DOUBLE);
		}
		//if the Price Type is Market or Market On Close, both Price Per Share and Percentage are Null
		else{
			pstm.setNull(2, Types.FLOAT);
			pstm.setNull(5, Types.DOUBLE);
		}
			
		
		
		//orderId is the most recent order, plus 1
		int orderId = getMostRecentOrderId(conn) + 1;
		pstm.setInt(3, orderId); 
		
		//Assume the order is initially placed right now
		//(not accounting for however trailing/hidden stops track time)
		Timestamp dateTimeNow= new Timestamp(System.currentTimeMillis()); // current datetime
		pstm.setTimestamp(4, dateTimeNow);
	
		
		pstm.setString(6, fullOrder.getPriceType());
		pstm.setString(7, fullOrder.getOrderType());
		
	//	pstm.executeUpdate();
		
		//If the order is Market or Market On Close, it is executed "immediately"
		//Thus, we need the current share price of the stock
		
		
//		pstm = conn.prepareStatement(sql);
		int transactionId = getMostRecentTransactionId(conn)+1;
		pstm.setInt(8, transactionId);
		if(fullOrder.getPriceType().equals("Market")||fullOrder.getPriceType().equals("Market On Close")){
			pstm.setFloat(9, (float)(finalPricePerShare * 0.05 * fullOrder.getNumShares()));
			pstm.setTimestamp(10, dateTimeNow);
			pstm.setFloat(11, finalPricePerShare);
		}
		else{
			pstm.setNull(9, Types.FLOAT);
			pstm.setNull(10, Types.TIMESTAMP);
			pstm.setNull(11, Types.FLOAT);
		}
	//	pstm.executeUpdate();
		
		
		
		//pstm = conn.prepareStatement(sql);
		pstm.setInt(12, fullOrder.getAccountId());
		pstm.setInt(13, fullOrder.getBrokerId());
		pstm.setInt(14, orderId);
		pstm.setInt(15, transactionId);
		pstm.setString(16, fullOrder.getStockId());
		pstm.executeUpdate();

		conn.commit();
		
	}
	/* Return the id of the last order to generate an id for a new order*/
	private static Integer getMostRecentOrderId(Connection conn) throws SQLException{
		
		String sql = "SELECT Id FROM Orders ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("Id");
		else 
			return 0;
	}
	/* Return the id of the last Transaction to generate an id for a new transaction*/
	private static Integer getMostRecentTransactionId(Connection conn) throws SQLException{
		
		String sql = "SELECT Id FROM Transaction ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("Id");
		else 
			return 0;
	}

	/* Return a list of all stocks*/
	public static List<Stock> getStockList(Connection conn) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "SELECT * FROM STOCK";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs= pstm.executeQuery();
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
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return list;
	}
	/* Return a list of all of the accounts a customer possesses*/
	public static List<Account> getAccountList(Connection conn, int clientId) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql="SELECT * FROM Account WHERE Client = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		/* Create list and add members to it from result set*/
		ResultSet rs = pstm.executeQuery();
		List<Account> list = new ArrayList<Account>(); 
		while(rs.next()){
			Account account = new Account(
									rs.getInt("Id"),
									rs.getDate("DateOpened"),
									rs.getInt("Client"));
			list.add(account);
		}
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return list;
	}


	public static List<FullOrder> getFullOrderList2(Connection conn, int clientId) throws SQLException {
		String sql = "SELECT F.* FROM Account A, FullOrder F WHERE A.Client = ? and A.Id = F.AccountId";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		ResultSet rs = pstm.executeQuery();
		List<FullOrder> list= new ArrayList<FullOrder>();
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
								rs.getString("StockId")	);
			list.add(order);
		}
		return list;
	}
	/* Return a list of suggested stocks to purchase*/
	public static List<Stock> getStockSuggestionList(Connection conn, int clientId) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION;"
				+ " SELECT * FROM Stock"
				+ " WHERE Type IN"
				+ " (SELECT S.Type FROM Account A, Client C, Orders O, Stock S, Trade Trd"
				+ " WHERE C.Id = ?"
				+ " AND A.Client = C.Id"
				+ " AND Trd.AccountId = A.Id"
				+ " AND Trd.OrderId = O.Id"
				+ " AND S.StockSymbol = Trd.StockId"
				+ " GROUP BY S.Type)"
				+ " LIMIT 20;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		pstm.execute();
		pstm.getMoreResults();
		/* Create list and add members to it from result set*/
		ResultSet rs = pstm.getResultSet();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
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
	/* This private method is used to fill out a trailing order */
	/* Since the user only fills out either the stop price or percentage but not both, */
	/* this calculates what the other should be*/

	private static double calculateStopPriceOrPercentage(Float pricePerShare, Float stopPrice, Double percentage) {
		//return Stop Price
		if(stopPrice == null || stopPrice==0)
			return pricePerShare * (percentage/100);
		//return Percentage
		else if(percentage == null || percentage==0)
			return stopPrice/pricePerShare*100;
		else 
			return 0;
	}
	/* Return the trailing history of a particular order*/
	public static List<TrailingHistory> getTrailingHistory(Connection conn, int orderId) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION;"
				+ " SELECT *"
				+ " FROM TrailHistory"
				+ " WHERE OrderId = ?"
				+ " ORDER BY DateTime ASC;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, orderId);
		pstm.execute();
		pstm.getMoreResults();
		/* Create list and add members to it from result set*/
		ResultSet rs= pstm.getResultSet();
		List<TrailingHistory> list = new ArrayList<TrailingHistory>();
		while(rs.next()){
			TrailingHistory trailingHistory = new TrailingHistory(
											rs.getInt("OrderId"),
											rs.getTimestamp("DateTime"),
											rs.getFloat("PricePerShare"));
			list.add(trailingHistory);
		}
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return list;
	}
	/* Find a particular transaction row based on its id*/
	public static Transaction findTransaction(Connection conn, int transactionId) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		
		/* Set up statement*/
		String sql = "START TRANSACTION;"
				+ " SELECT * FROM Transaction WHERE Id = ?;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, transactionId);
		pstm.execute();
		pstm.getMoreResults();
		/* Check result set and return the result */
		ResultSet rs = pstm.getResultSet();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		if(rs.next()){
			Transaction transaction = new Transaction(
											rs.getInt("Id"),
											rs.getFloat("Fee"),
											rs.getTimestamp("DateTime"),
											rs.getFloat("PricePerShare"));
			return transaction;
		}
		else
			return null;
	}
	/* Return the hidden stop history for a particular order*/
	public static List<History> getHiddenStopHistory(Connection conn, String stockId, Timestamp fromDate, Timestamp toDate) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION;"
				+ " SELECT * FROM History "
				+ " WHERE StockSymbol = ?"
				+ " AND DateTime >= ?"
				+ " AND DateTime <= ?"
				+ " ORDER BY DateTime ASC;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockId);
		pstm.setTimestamp(2, fromDate);
		/* If their is no toDate, which represents when the order was executed
		 * assume set toDate to null*/
		if(toDate!=null)
			pstm.setTimestamp(3, toDate);
		else
			pstm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pstm.execute();
		pstm.getMoreResults();
		/* Create list and add members to it from result set*/
		ResultSet rs= pstm.getResultSet();
		List<History> list = new ArrayList<History>();
		while(rs.next()){
			History history = new History(
								rs.getString("StockSymbol"),
								rs.getTimestamp("DateTime"),
								rs.getFloat("PricePerShare"));
			list.add(history);
		}
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return list;
	}
		
	
	/* Return the history of a stock from some period of time*/
	public static List<History> getStockHistoryList(Connection conn, String stockSymbol, Date fromDateParsed, Date toDateParsed) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION;"
				+ " SELECT *"
				+ " FROM History"
				+ " WHERE StockSymbol = ?"
				+ " AND DateTime > TIMESTAMP(?)"
				+ " AND DateTime < TIMESTAMP(?)"
				+ " ORDER BY DATETIME ASC;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		pstm.setDate(2, fromDateParsed);
		pstm.setDate(3, toDateParsed);
		pstm.execute();
		pstm.getMoreResults();
		/* Create list and add members to it from result set*/
		ResultSet rs = pstm.getResultSet();
		List<History> list = new ArrayList<History>();
		while(rs.next()){
			History history = new History(
								rs.getString("StockSymbol"),
								rs.getTimestamp("DateTime"),
								rs.getFloat("PricePerShare")
								);
			list.add(history);
		}
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return list;
	}

	/* find an order based upon its id*/
	public static Orders findOrder(Connection conn, int orderId) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION;"
				+ " SELECT * FROM Orders WHERE Id = ?;"
				+ " COMMIT;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, orderId);
		pstm.execute();
		pstm.getMoreResults();
		ResultSet rs = pstm.getResultSet();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		/* Check result set and return the result */
		if(rs.next()){
			Orders order = new Orders(
								rs.getInt("NumShares"),
								rs.getFloat("PricePerShare"),
								rs.getInt("Id"),
								rs.getTimestamp("DateTime"),
								rs.getDouble("Percentage"),
								rs.getString("PriceType"),
								rs.getString("OrderType"));
			return order;
		}
		return null;
	}
	/* Search for stocks based upon a set of keywords*/
	public static List<Stock> searchStocks(Connection conn, List<String> searchList) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT S.* FROM Stock S WHERE CompanyName LIKE ?";
		
		/* For each keyword, extend the SQL statement*/
		for(int i=1; i<searchList.size();++i){
			sql += " OR CompanyName LIKE ?";
		}
		
		pstm = conn.prepareStatement(sql);
		
		for(int i=1; i<searchList.size()+1;++i){
			pstm.setString(i, "%" + searchList.get(i-1) + "%");
		}
		ResultSet rs =pstm.executeQuery();
		
		/* Create list and add members to it from result set*/
		List<Stock> stockList = new ArrayList<Stock>();
		while(rs.next()){
			Stock stock = new Stock(
							rs.getString("StockSymbol"),
							rs.getString("CompanyName"),
							rs.getString("Type"),
							rs.getFloat("PricePerShare"),
							rs.getInt("NumShares"));
			stockList.add(stock);
		}
		sql = "COMMIT";
		pstm = conn.prepareStatement(sql);
		pstm.execute();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return stockList;
	}
	/* Return a list of a client's recent orders*/
	public static List<FullOrder> getRecentOrders(Connection conn, int clientId, String stockSymbol) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT F.*"
				+ " FROM Account A, FullOrder F"
				+ " WHERE A.Client = ? AND F.AccountId = A.Id AND F.StockId = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		pstm.setString(2, stockSymbol);
		/* Create list and add members to it from result set*/
		ResultSet rs = pstm.executeQuery();
		List<FullOrder> list = new ArrayList<FullOrder>();
		while(rs.next()){
			FullOrder fullOrder = new FullOrder(
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
								rs.getString("StockId"));
			list.add(fullOrder);
		}
		sql = "COMMIT";
		pstm = conn.prepareStatement(sql);
		pstm.execute();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return list;
	}
	/* Find a stock based on its stock symbol*/
	public static Stock findStock(Connection conn, String stockSymbol) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT * FROM Stock WHERE StockSymbol = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		/* Create list and add members to it from result set*/
		ResultSet rs = pstm.executeQuery();
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		
		if(rs.next()){
			Stock stock = new Stock(
							rs.getString("StockSymbol"),
							rs.getString("CompanyName"),
							rs.getString("Type"),
							rs.getFloat("PricePerShare"),
							rs.getInt("NumShares"));
			return stock;					
		}
		else
			return null;
	}
	/* Return the id of a client's broker*/
	public static Integer getBrokerId(Connection conn, Integer clientId) throws SQLException {
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT BrokerId FROM Client WHERE Id = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		/* Create list and add members to it from result set*/
		ResultSet rs = pstm.executeQuery();
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		if(rs.next()){
			return rs.getInt("BrokerId");
		}
		return null;
	}
	/* Return the number of shares a client has in their account*/
	public static int getSharesInAccount(Connection conn, Integer accountId, String stockSymbol) throws SQLException{
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql="SELECT *"
				+ " FROM HasStock"
				+ " WHERE AccountId = ? AND StockSymbol = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, accountId);
		pstm.setString(2, stockSymbol);
		ResultSet rs = pstm.executeQuery();
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		/* Check result set and return the result */
		if(rs.next())
			return rs.getInt("NumShares");
		else
			return 0;
	}
	/* Return a list of stocks that have sold best and relevent information*/
	public static List<BestSeller> getBestSellerList(Connection conn) throws SQLException{
		/* Turn off auto commit so a transaction may be executed */
		conn.setAutoCommit(false);
		/* Set up statement*/
		String sql="START TRANSACTION;"
				+ " SELECT 	Trd1.StockId,"
				+ " SUM(Trd2.TotalNumShares) AS TotalShares"
				+ " FROM Trade Trd1" 
				+ "	INNER JOIN" 	
				+ "		(SELECT O.Id," 
				+ " 			SUM(O.Numshares) AS TotalNumShares"
				+ " 	FROM Orders O"
				+ " 	WHERE O.OrderType = 'Sell'"
				+ " 	GROUP BY O.Id"
				+ "		ORDER BY NumShares DESC"
				+ " 	LIMIT 5)" 
				+ " AS Trd2"
				+ " ON Trd1.OrderId = Trd2.Id;"
				+ " COMMIT;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		pstm.getMoreResults();
		/* Create list and add members to it from result set*/
		ResultSet rs =pstm.getResultSet();
		
		List<BestSeller> list = new ArrayList<BestSeller>();
		while(rs.next()){
			BestSeller bs = new BestSeller(
								rs.getString("StockId"),
								rs.getInt("TotalShares")
								);
			list.add(bs);
		}
		/* Commit to reestablish autocommmit mode*/
		conn.commit();
		return list;
	}
	
}
	

