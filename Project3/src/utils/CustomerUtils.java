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
import beans.FullOrder;
import beans.History;
import beans.Orders;
import beans.Portfolio;
import beans.Stock;
import beans.TrailingHistory;
public class CustomerUtils {

	
	
	
	
	public static List<Portfolio> getStockPortfolio(Connection conn, int customerId) throws SQLException{
		String sql="select A.Id, S.stocksymbol, (Sum(O1.numshares) - Sum(O2.numshares)) As TotalShares"
			+ " from Account A, Orders O1, Orders O2, Trade Trd, Transaction trns, Stock S"
			+ " where trd.accountId= a.id"
			+ " and trd.transactionid = trns.id"
			+ " and trd.stockid = s.stocksymbol"
			+ " and trd.orderid = O2.id"
			+ " and O1.orderType = 'Buy'"
			+ " and O2.orderType= 'Sell'"
			+ " Group by S.stocksymbol;";
	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, customerId);
		ResultSet rs = pstm.executeQuery();
		List<Portfolio> list = new ArrayList<Portfolio>();
		while(rs.next()){
			String id = rs.getString("id");
			String stockSymbol = rs.getString("stockSymbol");
			int totalShares = rs.getInt("totalShares");
			Portfolio portfolio = new Portfolio(id, stockSymbol, totalShares);
			list.add(portfolio);
		}
	
	
	return list;
	}

	public static List<Orders> getOrderList(Connection conn, int clientId) throws SQLException {
		String sql = "SELECT O.*"
				+ " FROM Account A, Trade Trd, Orders O"
				+ " WHERE A.Client = ? AND A.Id = Trd.AccountId AND Trd.OrderId = O.Id"
				+ " ORDER BY DateTime DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		ResultSet rs = pstm.executeQuery();
		List<Orders> list = new ArrayList<Orders>();
		while(rs.next()){
			int numShares = rs.getInt("NumShares");
			int pricePerShare = rs.getInt("PricePerShare");
			int id = rs.getInt("Id");
			Timestamp dateTime = rs.getTimestamp("DateTime");
			double percentage = rs.getDouble("percentage");
			String priceType = rs.getString("PriceType");
			String orderType = rs.getString("OrderType");
			Orders orders = new Orders(numShares, pricePerShare, id, dateTime, percentage, priceType, orderType);
			list.add(orders);
		}
		return list;
	}

	public static void placeOrder(Connection conn, String stockSymbol, String orderType, String priceType, Integer numSharesParsed, Float pricePerShareParsed, Integer percentageParsed, Integer accountId,
			Integer brokerId) throws SQLException {
		String sql=null;
		PreparedStatement pstm = null;
		
		float stockPrice=0;
		//Insert into Order Table
		
		//If this is a trailing stop, we need to determine the current Price Per Share of the stock
		// in order to determine what the stop price and percentage to stop is.
		if(priceType.equals("Trailing Stop")){
			sql="SELECT PricePerShare FROM STOCK WHERE StockSymbol=?";
			pstm=conn.prepareStatement(sql);
			pstm.setString(1, stockSymbol);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			stockPrice = rs.getFloat("PricePerShare");
		}
		
		sql= "INSERT INTO Orders (NumShares, PricePerShare, Id, DateTime, Percentage, PriceType, OrderType)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		pstm = conn.prepareStatement(sql);
		
		pstm.setInt(1, numSharesParsed);
		
		//if the Price Type is a Trailing Stop, we need to determine the Stop Price and the Percentage.
		if(priceType.equals("Trailing Stop")){
			
			double x=calculateStopPriceOrPercentage(stockPrice, pricePerShareParsed, percentageParsed);

			if(pricePerShareParsed==0){
				// calculate the Price Per Share to stop at
				pstm.setFloat(2, (float)x);
				pstm.setDouble(5, percentageParsed);
			}
			else if(percentageParsed==0){
				// calculate the Percentage to stop at
				pstm.setFloat(2, pricePerShareParsed);
				pstm.setDouble(5, x);
			}
			else{
				pstm.setNull(2, Types.FLOAT);
				pstm.setNull(5, Types.DOUBLE);
			}
		}
		//if the Price Type is a Hidden Stop, we only need to worry about the Price Per Share
		else if(priceType.equals("Hidden Stop")){
			pstm.setFloat(2, pricePerShareParsed);
			pstm.setNull(5, Types.DOUBLE);
		}
		//if the Price Type is Market or Market On Close, both Price Per Share and Percentage are Null
		else{
			pstm.setFloat(2, Types.FLOAT);
			pstm.setDouble(5, Types.DOUBLE);
		}
			
		
		
		//orderId is the most recent order, plus 1
		int orderId = getMostRecentOrderId(conn) + 1;
		pstm.setInt(3, orderId); 
		
		//Assume the order is initially placed right now
		//(not accounting for however trailing/hidden stops track time)
		Timestamp dt= new Timestamp(System.currentTimeMillis()); // current datetime
		pstm.setTimestamp(4, dt);
	
		
		pstm.setString(6, priceType);
		pstm.setString(7, orderType);
		
		pstm.executeUpdate();
		
		//If the order is Market or Market On Close, it is executed "immediately"
		//Thus, we need the current share price of the stock
		float finalPricePerShare=0;
		if(priceType.equals("Market")||priceType.equals("Market On Close")){
			sql = "SELECT PricePerShare	FROM Stock"
					+ "	WHERE StockSymbol = ?";
			pstm = conn.prepareCall(sql);
			pstm.setString(1, stockSymbol);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			finalPricePerShare = rs.getFloat("PricePerShare");
		}
		
		sql = "INSERT INTO Transaction(Id, Fee, DateTime, PricePerShare)"
				+ " VALUES(?, ?, ?, ?)";
		pstm = conn.prepareCall(sql);
		int transactionId = getMostRecentTransactionId(conn)+1;
		pstm.setInt(1, transactionId);
		if(priceType.equals("Market")||priceType.equals("Market On Close")){
			pstm.setFloat(2, (float)(finalPricePerShare * 0.05));
			pstm.setTimestamp(3, dt);
			pstm.setFloat(4, finalPricePerShare);
		}
		else{
			pstm.setNull(2, Types.FLOAT);
			pstm.setNull(3, Types.TIMESTAMP);
			pstm.setNull(4, Types.FLOAT);
		}
		pstm.executeUpdate();
		
		
		
		sql = "INSERT INTO Trade(AccountId, BrokerId, OrderId, TransactionId, StockId)"
				+ "VALUES(?, ?, ?, ?, ?)";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, accountId);
		pstm.setInt(2, brokerId);
		pstm.setInt(3, orderId);
		pstm.setInt(4, transactionId);
		pstm.setString(5, stockSymbol);
		pstm.executeUpdate();
		
	}
	
private static Integer getMostRecentOrderId(Connection conn) throws SQLException{
		
		String sql = "SELECT Id FROM Orders ORDER BY Id DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("id");
		else 
			return 0;
	}
private static Integer getMostRecentTransactionId(Connection conn) throws SQLException{
		
		String sql = "SELECT Id FROM Transaction ORDER BY Id DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("id");
			
		
		else 
			return 0;
	}

	public static List<Stock> getStockList(Connection conn) throws SQLException {
		String sql = "SELECT * FROM STOCK";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs= pstm.executeQuery();
		List<Stock> list = new ArrayList<Stock>();
		while(rs.next()){
			Stock stock = new Stock(
								rs.getString("StockSymbol"),
								rs.getString("CompanyName"),
								rs.getString("Type"),
								rs.getFloat("PricePerShare"));
			list.add(stock);
		}
		return list;
	}

	public static List<Account> getAccountList(Connection conn, int clientId) throws SQLException {
		String sql="SELECT * FROM Account WHERE Client = ?";// TODO Auto-generated method stub
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

	public static List<Stock> getStockSuggestionList(Connection conn, int clientId) throws SQLException {
		String sql = "SELECT * FROM Stock"
				+ " WHERE Type = "
				+ " (SELECT S.Type FROM Account A, Client C, Orders O, Stock S, Trade Trd"
				+ " WHERE C.Id = ?"
				+ " AND A.Client = C.Id"
				+ " AND Trd.AccountId = A.Id"
				+ " AND Trd.OrderId = O.Id"
				+ " AND S.StockSymbol = Trd.StockId"
				+ " GROUP BY S.Type)"
				+ " LIMIT 20";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
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
	
	private static double calculateStopPriceOrPercentage(float pricePerShare, float stopPrice, double percentage) {
		if(stopPrice==0)
			return pricePerShare * (percentage/100);
		else if(percentage==0)
			return stopPrice/pricePerShare*100;
		else 
			return 0;
	}

	public static List<TrailingHistory> getTrailingHistory(Connection conn, int orderId) throws SQLException {
		String sql = "SELECT * FROM TrailHistory WHERE OrderId = ? ORDER BY DateTime ASC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, orderId);
		ResultSet rs= pstm.executeQuery();
		List<TrailingHistory> list = new ArrayList<TrailingHistory>();
		while(rs.next()){
			TrailingHistory trailingHistory = new TrailingHistory(
											rs.getInt("OrderId"),
											rs.getTimestamp("DateTime"),
											rs.getFloat("PricePerShare"));
			list.add(trailingHistory);
		}
		return list;
	}

	public static Transaction findTransaction(Connection conn, int transactionId) throws SQLException {
		String sql = "SELECT * FROM Transaction WHERE Id = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, transactionId);
		ResultSet rs = pstm.executeQuery();
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

	public static List<History> getHiddenStopHistory(Connection conn, String stockId, int orderId) throws SQLException {
		String sql = "SELECT DateTime FROM Orders WHERE Id = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, orderId);
		ResultSet rs = pstm.executeQuery();
		if(!rs.next())
			return null;
		else{
			Timestamp dateTime = rs.getTimestamp("DateTime");
			sql = "SELECT * FROM History WHERE StockSymbol = ? AND DateTime > ? ORDER BY DateTime DESC";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stockId);
			pstm.setTimestamp(2, dateTime);
			rs= pstm.executeQuery();
			List<History> list = new ArrayList<History>();
			while(rs.next()){
				History history = new History(
										rs.getString("StockSymbol"),
										rs.getTimestamp("DateTime"),
										rs.getFloat("PricePerShare"));
				list.add(history);
			}
			return list;
		}
		
	}

	public static List<History> getStockHistoryList(Connection conn, String stockSymbol, Date fromDateParsed, Date toDateParsed) throws SQLException {
		String sql = "SELECT * FROM History WHERE StockSymbol = ? AND DateTime > TIMESTAMP(?) AND DateTime < TIMESTAMP(?) ORDER BY DATETIME ASC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockSymbol);
		pstm.setDate(2, fromDateParsed);
		pstm.setDate(3, toDateParsed);
		ResultSet rs = pstm.executeQuery();
		List<History> list = new ArrayList<History>();
		while(rs.next()){
			History history = new History(
								rs.getString("StockSymbol"),
								rs.getTimestamp("DateTime"),
								rs.getFloat("PricePerShare")
								);
			list.add(history);
		}
		return list;
	}
}
	

