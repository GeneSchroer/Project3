package beans;

import java.io.Serializable;

public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;
	private String stockSymbol;
	private String companyName;
	private String type;
	private Float pricePerShare;
	private Integer numShares;
	public Stock(String stockSymbol, String companyName, String type, float pricePerShare){
		this.stockSymbol = stockSymbol;
		this.companyName = companyName;
		this.type = type;
		this.pricePerShare = pricePerShare;
	}
	public Stock(String stockSymbol, String companyName, String type, float pricePerShare, int numShares){
		this.stockSymbol = stockSymbol;
		this.companyName = companyName;
		this.type = type;
		this.pricePerShare = pricePerShare;
		this.numShares = numShares;
	}
	public String getStockSymbol(){ return stockSymbol; }
	public void setStockSymbol(String stockSymbol){ this.stockSymbol = stockSymbol; }
	
	public String getCompanyName(){ return companyName;	}
	public void setCompanyName(String companyName){ this.companyName = companyName; }
	
	public String getType(){ return type; }
	public void setType(String type){ this.type = type; }
	
	public Float getPricePerShare(){ return pricePerShare; }
	public void setPricePerShare(float pricePerShare){ this.pricePerShare = pricePerShare; }
	
	public Integer getNumShares(){ return numShares; }
	public void setNumShares(int numShares){ this.numShares = numShares; }
}
