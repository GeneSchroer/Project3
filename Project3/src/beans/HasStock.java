package beans;

import java.io.Serializable;

public class HasStock implements Serializable{

	private Integer accountId;
	private String stockSymbol;
	private Integer	totalShares;
	private Float	pricePerShare;
	private static final long serialVersionUID = 1L;
	public HasStock(int accountId, String stockSymbol, int totalShares){
		this.accountId = accountId;
		this.stockSymbol = stockSymbol;
		this.totalShares = totalShares;
	}
	public HasStock(int accountId, String stockSymbol, int totalShares, float pricePerShare){
		this.accountId = accountId;
		this.stockSymbol = stockSymbol;
		this.totalShares = totalShares;
		this.pricePerShare = pricePerShare;
	}
	public Integer getAccountId(){ return accountId; }
	public void setAccountId(Integer accountId){ this.accountId = accountId; }
	
	public String getStockSymbol(){ return stockSymbol; }
	public void setStockSymbol(String stockSymbol){ this.stockSymbol = stockSymbol; }
	
	public Integer getTotalShares(){ return totalShares; }
	public void setTotalShares(Integer totalShares){ this.totalShares = totalShares; }
	
	public Float getPricePerShare(){ return pricePerShare; }
	public void setPricePerShare(Float pricePerShare){ this.pricePerShare = pricePerShare; }
}
