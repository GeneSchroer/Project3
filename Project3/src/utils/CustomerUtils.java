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
import beans.HasStock;
import beans.History;
import beans.Orders;
import beans.Portfolio;
import beans.Stock;
import beans.TrailingHistory;
public class CustomerUtils {

	
	
	
	
	public static List<HasStock> getStockPortfolio(Connection conn, int customerId) throws SQLException{
		conn.setAutoCommit(false);
		
		String sql="SELECT H.*, S.PricePerShare"
				+ " FROM Account A, HasStock H, Stock S"
				+ " WHERE A.Client = ?"
				+ " AND A.Id = H.AccountId"
				+ " AND H.StockSymbol = S.StockSymbol"
				+ " ORDER BY AccountId ASC";
	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, customerId);
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

	public static List<Orders> getOrderList(Connection conn, int clientId) throws SQLException {
		String sql = "START TRANSACTION;"
				+ " SELECT O.*"
				+ " FROM Account A, Trade Trd, Orders O"
				+ " WHERE A.Client = ? AND A.Id = Trd.AccountId AND Trd.OrderId = O.Id"
				+ " ORDER BY DateTime DESC;";
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
	
private static Integer getMostRecentOrderId(Connection conn) throws SQLException{
		
		String sql = "SELECT Id FROM Orders ORDER BY Id DESC LIMIT 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if(rs.next())
			return rs.getInt("Id");
		else 
			return 0;
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
								rs.getFloat("PricePerShare"),
								rs.getInt("NumShares"));
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
				+ " WHERE Type IN"
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

	public static List<History> getHiddenStopHistory(Connection conn, String stockId, Timestamp fromDate, Timestamp toDate) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql =  " SELECT * FROM History "
				+ " WHERE StockSymbol = ?"
				+ " AND DateTime >= ?"
				+ " AND DateTime <= ?"
				+ " ORDER BY DateTime ASC;";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, stockId);
		pstm.setTimestamp(2, fromDate);
		if(toDate!=null)
			pstm.setTimestamp(3, toDate);
		else
			pstm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		ResultSet rs= pstm.executeQuery();
		List<History> list = new ArrayList<History>();
		while(rs.next()){
			History history = new History(
								rs.getString("StockSymbol"),
								rs.getTimestamp("DateTime"),
								rs.getFloat("PricePerShare"));
			list.add(history);
		}
		pstm = conn.prepareStatement("COMMIT");
		pstm.execute();
		conn.commit();
		return list;
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

	public static void changePassword(Connection conn, String userName, String password1) throws SQLException {
		String sql = "UPDATE UserAccount SET Password = ? WHERE UserName = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, password1);
		pstm.setString(2, userName);
		pstm.executeUpdate();
	}

	public static Orders findOrder(Connection conn, int orderId) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		
		sql = "SELECT * FROM Orders WHERE Id = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, orderId);
		ResultSet rs = pstm.executeQuery();
		sql = "COMMIT";
		pstm = conn.prepareStatement(sql);
		pstm.execute();
		conn.commit();
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

	public static List<Stock> searchStocks(Connection conn, List<String> searchList) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT S.* FROM Stock S WHERE CompanyName LIKE ?";
		
		for(int i=1; i<searchList.size();++i){
			sql += " OR CompanyName LIKE ?";
		}
		pstm = conn.prepareStatement(sql);
		for(int i=1; i<searchList.size()+1;++i){
			pstm.setString(i, "%" + searchList.get(i-1) + "%");
		}
		ResultSet rs =pstm.executeQuery();
		
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
		return stockList;
	}

	public static List<FullOrder> getRecentOrders(Connection conn, int clientId, String stockSymbol) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT F.*"
				+ " FROM Account A, FullOrder F"
				+ " WHERE A.Client = ? AND F.AccountId = A.Id AND F.StockId = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		pstm.setString(2, stockSymbol);
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
		return list;
	}

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
							rs.getInt("NumShares"));
			return stock;					
		}
		else
			return null;
	}

	public static Integer getBrokerId(Connection conn, Integer clientId) throws SQLException {
		String sql = "START TRANSACTION";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.execute();
		sql = "SELECT BrokerId FROM Client WHERE Id = ?";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, clientId);
		ResultSet rs = pstm.executeQuery();
		pstm = conn.prepareStatement("COMMIT");
		if(rs.next()){
			return rs.getInt("BrokerId");
		}
		return null;
	}

	public static int getSharesInAccount(Connection conn, Integer accountId, String stockSymbol) throws SQLException{
		conn.setAutoCommit(false);
		String sql="SELECT *"
				+ " FROM HasStock"
				+ " WHERE AccountId = ? AND StockSymbol = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, accountId);
		pstm.setString(2, stockSymbol);
		ResultSet rs = pstm.executeQuery();
		conn.commit();
		if(rs.next())
			return rs.getInt("NumShares");
		else
			return 0;
	}
}
	

