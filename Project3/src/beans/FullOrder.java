package beans;

import java.sql.Timestamp;

	
public class FullOrder extends Orders {
	
	private Integer accountId;
	private Integer brokerId;
	private String stockId;
	private Integer transactionId;
	private Integer fee;
	private Timestamp finalDateTime;
	private Float finalPricePerShare;
	public FullOrder(int numShares, float pricePerShare, int id, Timestamp dateTime, double percentage, String price,
			String order, int transactionId, int fee, Timestamp finalDateTime, float finalPricePerShare) {
		super(numShares, pricePerShare, id, dateTime, percentage, price, order);
		this.transactionId = transactionId;
		this.fee = fee;
		this.finalDateTime = finalDateTime;
		this.finalPricePerShare = finalPricePerShare;
	}
	public FullOrder(int numShares, float pricePerShare, int id, Timestamp dateTime, double percentage, String price,
			String order, int accountId, int brokerId, String stockId, int fee, Timestamp finalDateTime, float finalPricePerShare) {
		super(numShares, pricePerShare, id, dateTime, percentage, price, order);
		this.accountId = accountId;
		this.brokerId = brokerId;
		this.stockId = stockId;
		this.fee = fee;
		this.finalDateTime = finalDateTime;
		this.finalPricePerShare = finalPricePerShare;
	}
	public Integer getTransactionId(){ return transactionId; }
	public void setTransactionId(int transactionId){ this.transactionId = transactionId; }
	
	public Integer getAccountId(){ return accountId; }
	public void setAccountId(int accountId){ this.accountId = accountId; }

	public Integer getBrokerId(){ return brokerId; }
	public void setBrokerId(int brokerId){ this.brokerId = brokerId; }
	
	public String getStockId(){ return stockId; }
	public void setStockId(String stockId){ this.stockId = stockId; }
	
	
	public Integer getFee(){ return fee; }
	public void setFee(int fee){ this.fee = fee; }
	
	public Timestamp getFinalDateTime(){ return finalDateTime; }
	public void setFinalDateTime(Timestamp finalDateTime){ this.finalDateTime = finalDateTime; }
	
	public Float getFinalPricePerShare(){ return finalPricePerShare; }
	public void setFinalPricePerShare(float finalPricePerShare){ this.finalPricePerShare = finalPricePerShare; }
}
