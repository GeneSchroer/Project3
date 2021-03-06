package beans;

import java.sql.Timestamp;

	
public class FullOrder extends Orders {
	private static final long serialVersionUID = 1L;
	private Integer transactionId;
	private Float fee;
	private Timestamp finalDateTime;
	private Float finalPricePerShare;
	private Integer accountId;
	private Integer brokerId;
	private String stockId;
	public FullOrder(int numShares, Float pricePerShare, int id, Timestamp dateTime, Double percentage, String priceType, String orderType,
			int transactionId, Float fee, Timestamp finalDateTime, Float finalPricePerShare, int accountId, int brokerId, String stockId){
		super(numShares, pricePerShare, id, dateTime, percentage, priceType, orderType);
		this.transactionId = transactionId;
		this.fee = fee;
		this.finalDateTime = finalDateTime;
		this.finalPricePerShare = finalPricePerShare;
		this.accountId = accountId;
		this.brokerId = brokerId;
		this.stockId = stockId;
	}
	public Integer getTransactionId(){ return transactionId; }
	public void setTransactionId(int transactionId){ this.transactionId = transactionId; }
	
	public Float getFee(){ return fee; }
	public void setFee(Float fee){ this.fee = fee; }
	
	public Timestamp getFinalDateTime(){ return finalDateTime; }
	public void setFinalDateTime(Timestamp finalDateTime){ this.finalDateTime = finalDateTime; }
	
	public Float getFinalPricePerShare(){ return finalPricePerShare; }
	public void setFinalPricePerShare(Float finalPricePerShare){ this.finalPricePerShare = finalPricePerShare; }
	
	public Integer getAccountId(){ return accountId; }
	public void setAccountId(int accountId){ this.accountId = accountId; }

	public Integer getBrokerId(){ return brokerId; }
	public void setBrokerId(int brokerId){ this.brokerId = brokerId; }
	
	public String getStockId(){ return stockId; }
	public void setStockId(String stockId){ this.stockId = stockId; }
	
	
	
	
}
