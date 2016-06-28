package beans;

import java.io.Serializable;

public class SummaryListing implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stockSymbol;
	private String companyName;
	private String type;
	private Float revenue;
	private Integer clientId;
	private String lastName;
	private String firstName;
	public SummaryListing(String stockSymbol, String companyName, Float revenue){
		this.stockSymbol = stockSymbol;
		this.companyName = companyName;
		this.revenue = revenue;
	}
	public SummaryListing(String type, Float revenue){
		this.type = type;
		this.revenue = revenue;
	}
	public SummaryListing(Integer clientId, String lastName, String firstName, Float revenue){
		this.clientId = clientId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.revenue = revenue;
	}
	
	public String getStockSymbol(){ return stockSymbol; }
	public void setStockSymbol(String stockSymbol){ this.stockSymbol = stockSymbol; }
	
	public String getCompanyName(){ return companyName; }
	public void setCompanyName(String companyName){ this.companyName = companyName; }
	
	public String getType(){ return type; }
	public void setType(String type){ this.type = type; }
	
	public Float getRevenue(){ return revenue; }
	public void setRevenue(Float revenue){ this.revenue = revenue; }
	
	public Integer getClientId(){ return clientId; }
	public void setClientId(Integer clientId){ this.clientId = clientId; }
	
	public String getLastName(){ return lastName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	
	public String getFirstName(){ return firstName; }
	public void setFirstName(String firstName){ this.firstName = firstName; }
	
}
