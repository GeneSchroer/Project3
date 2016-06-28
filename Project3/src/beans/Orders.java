package beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class Orders implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer numShares;
	private Float pricePerShare;
	private Integer id;
	private Timestamp dateTime;
	private Double percentage;
	private String priceType;
	protected String orderType;
	
	public Orders(int numShares, Float pricePerShare, int id, Timestamp dateTime, Double percentage, String price, String order){
		this.numShares = numShares;
		this.pricePerShare = pricePerShare;
		this.id = id;
		this.dateTime = dateTime;
		this.percentage = percentage;
		this.priceType = price;
		this.orderType = order;
	}
	
	public Integer getNumShares(){ return numShares; }
	public void setNumShares(Integer numShares){ this.numShares = numShares; }
	
	public Float getPricePerShare(){ return pricePerShare; }
	public void setPricePerShare(Float pricePerShare){ this.pricePerShare = pricePerShare; }
	
	public Integer getId(){ return id; }
	public void setId(Integer id){ this.id = id; }
	
	public Timestamp getDateTime(){ return dateTime; }
	public void setDateTime(Timestamp dateTime){ this.dateTime = dateTime; }
	
	public Double getPercentage(){ return percentage; }
	public void setPercentage(Double percentage){ this.percentage = percentage; }
	
	public String getPriceType(){ return priceType; }
	public void setPriceType(String price){ this.priceType = price; }
	
	public String getOrderType(){ return orderType; }
	public void setOrderType(String order){ this.orderType = order; }
	
	
}
