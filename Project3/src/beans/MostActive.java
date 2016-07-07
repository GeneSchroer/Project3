package beans;

public class MostActive {
	private Integer timesTraded;
	private String stockSymbol;
	private String companyName;
	public MostActive(Integer timesTraded, String stockSymbol, String companyName){
		this.timesTraded = timesTraded;
		this.stockSymbol = stockSymbol;
		this.companyName = companyName;
	}
	public Integer getTimesTraded(){ return timesTraded; }
	public void setTimesTraded(Integer timesTraded){ this.timesTraded = timesTraded; }
	
	public String getStockSymbol(){ return stockSymbol; }
	public void setStockSymbol(String stockSymbol){ this.stockSymbol = stockSymbol; }
	
	public String getCompanyName(){ return companyName; }
	public void setCompanyName(String companyName){ this.companyName = companyName; }
}
