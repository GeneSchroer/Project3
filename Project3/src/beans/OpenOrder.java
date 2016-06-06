package beans;

import java.sql.Timestamp;

	
public class OpenOrder extends Orders {
	private Integer accountId;
	private String stockId;
	private String companyName;
	public OpenOrder(int numShares, float pricePerShare, int id, Timestamp dateTime, double percentage, String price,
			String order, int accountId, String stockId, String companyName) {
		super(numShares, pricePerShare, id, dateTime, percentage, price, order);
		this.accountId=accountId;
		this.stockId = stockId;
		this.companyName=companyName;
	}
	public Integer getAccountId(){ return accountId; }
	public void setAccountId(int accountId){ this.accountId = accountId; }
	
	public String getStockId(){ return stockId; }
	public void setStockId(String stockId){ this.stockId = stockId; }
	
	public String getCompanyName(){ return companyName; }
	public void setCompanyName(String companyName){ this.companyName = companyName; }
}
