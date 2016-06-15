package beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class TrailingHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer orderId;
	private Timestamp dateTime;
	private Float pricePerShare; 
	public TrailingHistory(int orderId, Timestamp dateTime, float pricePerShare){
		this.orderId = orderId;
		this.dateTime = dateTime;
		this.pricePerShare = pricePerShare;
	}
	public Integer getOrderId(){ return orderId; }
	public void setOrderId(int orderId){ this.orderId = orderId; }
	
	public Timestamp getDateTime(){ return dateTime; }
	public void setDateTime(Timestamp dateTime){ this.dateTime = dateTime; }
	
	public Float getPricePerShare(){ return pricePerShare; }
	public void setPricePerShare(float pricePerShare){ this.pricePerShare = pricePerShare; }
}
