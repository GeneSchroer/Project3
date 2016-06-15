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
	
	public Orders(int numShares, float pricePerShare, int id, Timestamp dateTime, double percentage, String price, String order){
		this.numShares = numShares;
		this.pricePerShare = pricePerShare;
		this.id = id;
		this.dateTime = dateTime;
		this.percentage = percentage;
		this.priceType = price;
		this.orderType = order;
	}
	
	public int getNumShares(){ return numShares; }
	public void setNumShares(int numShares){ this.numShares = numShares; }
	
	public float getPricePerShare(){ return pricePerShare; }
	public void setPricePerShare(float pricePerShare){ this.pricePerShare = pricePerShare; }
	
	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	
	public Timestamp getDateTime(){ return dateTime; }
	public void setDateTime(Timestamp dateTime){ this.dateTime = dateTime; }
	
	public double getPercentage(){ return percentage; }
	public void setPercentage(double percentage){ this.percentage = percentage; }
	
	public String getPriceType(){ return priceType; }
	public void setPriceType(String price){ this.priceType = price; }
	
	public String getOrderType(){ return orderType; }
	public void setOrderType(String order){ this.orderType = order; }
	
	
}
