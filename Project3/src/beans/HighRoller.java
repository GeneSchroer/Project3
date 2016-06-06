package beans;

public class HighRoller {
	private String lastName;
	private String firstName;
	private Integer id;
	private Float revenue;
	public HighRoller(String lastName, String firstName, int id, float revenue){
		this.lastName = lastName;
		this.firstName = firstName;
		this.id = id;
		this.revenue = revenue;
	}
	public String getLastName(){ return lastName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	
	public String getFirstName(){ return firstName; }
	public void setFirstName(String firstName){ this.firstName = firstName; }
	
	public Integer getId(){ return id; }
	public void setId(int id){ this.id = id; }
	
	public Float getRevenue(){ return revenue; }
	public void setRevenue(float revenue){ this.revenue = revenue; }
}
