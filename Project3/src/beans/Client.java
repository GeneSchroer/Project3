package beans;

public class Client extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private Integer rating;
	private String creditCardNumber;
	private Integer id;
	private Integer brokerId;
	public Client(){
		super();
		this.email=null;
		this.rating=null;
		this.creditCardNumber=null;
		this.id=null;
	}
	
	public Client(int SSN, String lastName, String firstName, String address, int zipCode, String telephone, 
			String email, int rating, String creditCardNumber, int brokerId){
		super(SSN, lastName, firstName, address, zipCode, telephone);
		this.email = email;
		this.rating = rating;
		this.creditCardNumber = creditCardNumber;
		this.id = SSN;
		this.brokerId = brokerId;
	}
	
	public String getEmail(){ return email; }
	public void setEmail(String email){ this.email = email; }
	
	public Integer getRating(){ return rating; }
	public void setRating(int rating){ this.rating = rating; }
	
	public String getCreditCardNumber(){ return creditCardNumber; }
	public void setCreditCardNumber(String creditCardNumber){ this.creditCardNumber = creditCardNumber; }
	
	public Integer getId(){ return id; }
	public void setId(int id){ this.id = id; }
	
	public Integer getBrokerId(){ return brokerId; }
	public void setBrokerId(int brokerId){ this.brokerId = brokerId; }
}
