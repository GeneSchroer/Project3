package beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class History implements Serializable {

	private static final long serialVersionUID = 1L;
	private String stockSymbol;
	private Timestamp dateTime;
	private Float pricePerShare;
	public History(String stockSymbol, Timestamp dateTime, float pricePerShare){
		this.stockSymbol = stockSymbol;
		this.dateTime = dateTime;
		this.pricePerShare = pricePerShare;
	}
	public String getStockSymbol(){ return stockSymbol; }
	public void setStockSymbol(String stockSymbol){ this.stockSymbol = stockSymbol; }
	
	public Timestamp getDateTime(){ return dateTime; }
	public void setDateTime(Timestamp dateTime){ this.dateTime = dateTime; }
	
	public Float getPricePerShare(){ return pricePerShare; }
	public void setPricePerShare(float pricePerShare){ this.pricePerShare = pricePerShare; }
	
}
