package beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private float fee;
	private Timestamp DateTime;
	private float pricePerShare;
	public Transaction(int id, float fee, Timestamp DateTime, float pricePerShare){
		this.id = id;
		this.fee = fee;
		this.DateTime = DateTime;
		this.pricePerShare = pricePerShare;
	}
	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	
	public float getFee(){ return fee; }
	public void setFee(float fee){ this.fee = fee; }
	
	public Timestamp getDateTime(){ return DateTime; }
	public void setDateTime(Timestamp DateTime){ this.DateTime = DateTime; }
	
	public float getPricePerShare(){ return pricePerShare; }
	public void setPricePerShare(float pricePerShare){ this.pricePerShare = pricePerShare; }
	
}
