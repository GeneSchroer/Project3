package beans;

import java.util.Date;


public class SalesReport extends Transaction{

	private String stockSymbol;
	private String companyName;
	private String type;
	private Integer numShares;
	private String orderType;
	private String priceType;
	
	public SalesReport(int id, float fee, Date DateTime, float pricePerShare, 
			String stockSymbol, String companyName, String type, int numShares, String orderType, String priceType) {
		super(id, fee, DateTime, pricePerShare);
		this.stockSymbol = stockSymbol;
		this.companyName = companyName;
		this.type = type;
		this.numShares = numShares;
		this.orderType = orderType;
		this.priceType = priceType;
	}

	public String getStockSymbol(){ return stockSymbol; }
	public void setStockSymbol(String stockSymbol){ this.stockSymbol = stockSymbol; }
	
	public String getCompanyName(){ return companyName; }
	public void setCompanyName(String companyName){ this.companyName = companyName; }
	
	public String getType(){ return type; }
	public void setType(String type){ this.type = type; }
	
	public int getNumShares(){ return numShares; }
	public void setNumShares(int numShares){ this.numShares = numShares; }
	
	public String getOrderType(){ return orderType;	}
	public void setOrderType(String orderType){ this.orderType = orderType; }
	
	public String getPriceType(){ return priceType; }
	public void setPriceType(String priceType){ this.priceType = priceType; }
}
