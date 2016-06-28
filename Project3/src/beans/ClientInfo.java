package beans;


public class ClientInfo extends Client{
	private static final long serialVersionUID = 1L;
	private String city;
	private String state;
	public ClientInfo(int SSN, String firstName, String lastName, String address, int zipCode, String telephone, 
			String email, int rating, String creditCardNumber, int brokerId, String city, String state){
		super(SSN, firstName, lastName, address, zipCode, telephone, 
				email, rating, creditCardNumber, brokerId);
		this.city = city;
		this.state = state;
	}
		public String getCity(){ return city; }
		public void setCity(String city){ this.city = city; }
		
		public String getState(){ return state; }
		public void setState(String state){ this.state = state; }
		
}
