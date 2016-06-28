package managers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Employee;
import beans.Location;
import beans.Person;
import beans.UserAccount;
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/managers/employeeList/doCreateEmployee"})
public class DoCreateEmployeeServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DoCreateEmployeeServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		int SSN=0;
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		
		int zipCode=0;
		String telephone=null;
		int id =0;
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		int hourlyRate = 0;
		Date startDate=null;
		
		String userType = request.getParameter("userType");
		boolean hasError=false;
		String errorStrSSN, errorStrLastName, errorStrFirstName, errorStrAddress, errorStrCity, errorStrState, errorStrZipCode, errorStrTelephone,
			errorStrId, errorStrHourlyRate;
		
		String errorStrStartDate = null;
		String errorStrUserName = null;
		String errorStrPassword = null;
		
		
		String dateNow = request.getParameter("now");
		Employee employee=null;
		UserAccount user = null;
		String regex=null;
		String regex2=null;
		
		
		
		// check for errors before adding an Employee to the database
		
		//SSN
		// Check if user input is an integer
		try{
			errorStrSSN=null;
			SSN = Integer.parseInt(request.getParameter("SSN"));
			if(SSN<0 || SSN>999999999){
				hasError=true;
				errorStrSSN="Invalid SSN!";	}	}
		catch(Exception e){
			hasError = true;
			errorStrSSN="Invalid SSN!";	}
		//Check to make sure this SSN is not already in use
		try {
			Person p = ManagerUtils.findPerson(conn, SSN);
			if (p != null){
				hasError=true;
				errorStrSSN="Error: SSN already in use!";	}	} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//LastName
		//allows for last names with multiple parts
		regex="[a-zA-Z]+[\\s[a-zA-z]]*";
		errorStrLastName=null;
		if(lastName==null || !lastName.matches(regex)){
			hasError=true;
			errorStrLastName="Last Name invalid!";	}
		
		//FirstName
		regex="[a-zA-Z]+";
		errorStrFirstName=null;
		if(firstName==null || !firstName.matches(regex)){
			hasError = true;
			errorStrFirstName="First Name invalid!";	}
		
		//Address
		//Regular expression with a number and any number of words
		regex="[0-9]+?[\\s[a-zA-Z]]{1,}[\\s[a-zA-Z]\\x2E]?";
		errorStrAddress=null;
		if(address==null || !address.matches(regex)){
			hasError=true;
			errorStrAddress="Address invalid!";	}
		
		//City
		//Regular Expression = Single-or-Multi named city
		regex = "[[A-Z][a-zA-z]][\\s[A-Z][a-zA-Z]]*";
		errorStrCity=null;
		if(city==null|| !city.matches(regex)){
			hasError=true;
			errorStrCity = "Error: Invalid City!";	}
		
		//State
		//might add a check for this at some point
		errorStrState=null;
		if(state==null|| !state.matches(regex)){
			hasError=true;
			errorStrState = "Error: Invalid State!";
		}
		
		//Zip Code
		try{
			errorStrZipCode=null;
			zipCode = Integer.parseInt(request.getParameter("zipCode"));
			if(zipCode<0 || zipCode>99999){
				hasError=true;
				errorStrZipCode = "Invalid Zip Code";
			}		
		}catch(Exception  e){
			hasError = true;
			errorStrZipCode="Invalid Zip Code!";
		}
		
		// Telephone
		regex="[0-9]{10}";
		regex2="[0-9]{3}\\x2D[0-9]{3}\\x2D[0-9]{4}";
		errorStrTelephone=null;
			telephone = request.getParameter("telephone");
		if(telephone==null||(!telephone.matches(regex)&&!telephone.matches(regex2))){
			hasError=true;
			errorStrTelephone="Invalid Telephone Number!";
		}
		
		
		//UserName
		if(userName==null){
			hasError = true;
			errorStrUserName = "Error: User Name cannot be null!";
		}
		
		//Password
		if(password==null){
			hasError=true;
			errorStrPassword = "Error: Password cannot be null!";
		}
		
		//Start Date
		//Check if were using the current date or another date
		if(dateNow!=null)
			startDate = new Date(System.currentTimeMillis());
		else{
			try{			
				//attempt to parse the current date;
				startDate = Date.valueOf(request.getParameter("startDate"));
				if(startDate.after(new Date(System.currentTimeMillis()))){
					hasError = true;
					errorStrStartDate = "Error: Start Date cannot be after the current date";
				}	}
			catch(Exception e){
				hasError=true;
				errorStrStartDate="Invalid Date!"; 	}	}
		
		
		//Hourly Rate
		try{
			errorStrHourlyRate=null;
			hourlyRate = Integer.parseInt(request.getParameter("hourlyRate"));
			if(hourlyRate<0){
				errorStrHourlyRate="Invalid Hourly Rate";
			}
		}
		catch(Exception e){
			hasError=true;
			errorStrHourlyRate="Invalid Id!";
		}
		
		String errorString = null;
		

		if(!hasError){
			try{
				employee = new Employee(SSN, firstName, lastName, address, zipCode, telephone, 
						ManagerUtils.getMostRecentEmployeeId(conn)+1, startDate, hourlyRate );
				Location location = new Location(zipCode, city, state);
			
				user = new UserAccount(userName, password, userType, employee.getId());
							
				ManagerUtils.addEmployee(conn, employee, location, user);
				
			}
			catch(SQLException e){
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		if(hasError){
		//set errorstring and employee then forward to views
		errorString = "There was an error when attempted to add a new employee."
				+ " Please try again.";
		request.setAttribute("errorString", errorString);
		request.setAttribute("errorStrSSN", errorStrSSN);
		request.setAttribute("errorStrLastName", errorStrLastName);
		request.setAttribute("errorStrFirstName", errorStrFirstName);
		request.setAttribute("errorStrAddress", errorStrAddress);
		request.setAttribute("errorStrCity", errorStrCity);
		request.setAttribute("errorStrCity", errorStrCity);
		request.setAttribute("errorStrZipCode", errorStrZipCode);
		request.setAttribute("errorStrTelephone", errorStrTelephone);
		request.setAttribute("errorStrUserName", errorStrUserName);
		request.setAttribute("errorStrPassword", errorStrPassword);
		request.setAttribute("errorStrStartDate", errorStrStartDate);
		request.setAttribute("errorStrHourlyRate", errorStrHourlyRate);
		
		
		request.setAttribute("SSN", SSN);
		request.setAttribute("lastName", lastName);
		request.setAttribute("firstName", firstName);
		request.setAttribute("address", address);
		request.setAttribute("city", city);
		request.setAttribute("state", state);
		request.setAttribute("zipCode", zipCode);
		request.setAttribute("telephone", telephone);
		request.setAttribute("id", id);
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);
		request.setAttribute("startDate", startDate);
		request.setAttribute("hourlyRate", hourlyRate);
		request.setAttribute("employee", employee);
		//If there's an error, stay on createEmployeeView
		RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/managers/createEmployeeView.jsp");
			dispatcher.forward(request, response);
		}
		
		//If everything worked, redirect to the employeeList
		else{
			response.sendRedirect(request.getContextPath() + "/managers/employeeList");
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
	
}