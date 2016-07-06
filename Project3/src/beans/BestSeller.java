package beans;

import java.io.Serializable;

public class BestSeller implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stockId;
	private Integer totalShares;
	public BestSeller(String stockId, Integer totalShares){
		this.stockId = stockId;
		this.totalShares = totalShares;
	}
	public String getStockId(){ return stockId; }
	public void setStockId(String stockId){ this.stockId = stockId; }
	
	public Integer getTotalShares(){ return totalShares; }
	public void setTotalShares(Integer totalShares){ this.totalShares = totalShares; }
}
