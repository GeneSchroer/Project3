package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Orders;
import beans.Portfolio;
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
	
}
