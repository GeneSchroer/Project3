package beans;

import java.sql.Date;

public class AccountDetails extends Account{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lastName;
	private String firstName;
	private String address;
	private Integer zipCode;
	private String telephone;
	private Integer brokerId;
	public AccountDetails(int id, Date dateOpened, int clientId, String lastName, String firstName, String address, Integer zipCode, String telephone/*, Integer brokerId*/) {
		super(id, dateOpened, clientId);
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.zipCode = zipCode;
		this.telephone = telephone;
		//this.brokerId = brokerId;
	}
	public String getLastName(){ return lastName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	
	public String getFirstName(){ return firstName; }
	public void setFirstName(String firstName){ this.firstName = firstName; }
	
	public String getAddress(){ return address; }
	public void setAddress(String address){ this.address = address; }
	
	public Integer getZipCode(){ return zipCode; }
	public void setZipCode(Integer zipCode){ this.zipCode = zipCode; }
	
	public String getTelephone(){ return telephone; }
	public void setTelephone(String telephone){ this.telephone = telephone; }
	
	public Integer getBrokerId(){ return brokerId; }
	public void setBrokerId(Integer brokerId){ this.brokerId = brokerId; }
}
