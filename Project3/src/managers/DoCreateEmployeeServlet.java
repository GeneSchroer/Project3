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
		int hourlyRate = 0;
		Date startDate=null;
		boolean hasError=false;
		String errorStrSSN, errorStrLastName, errorStrFirstName, errorStrAddress, errorStrCity, errorStrState, errorStrZipCode, errorStrTelephone,
			errorStrId, errorStrStartDate, errorStrHourlyRate;

		Employee employee=null;
		
		// check for errors before adding an Employee to the database
		
		//SSN
		// Check if user input is an integer
		try{
			errorStrSSN=null;
			SSN = Integer.parseInt(request.getParameter("SSN"));
			if(SSN<0 || SSN>999999999){
				hasError=true;
				errorStrSSN="Invalid SSN!";
			}
				
		}catch(Exception e){
			
			hasError = true;
			errorStrSSN="Invalid SSN!";
		}
		
		//LastName
		String regex=null;
		String regex2=null;
		regex="[a-zA-Z]+";
		errorStrLastName=null;
		if(lastName==null || !lastName.matches(regex)){
			hasError=true;
			errorStrLastName="Last Name invalid!";
		}
		//FirstName
		errorStrFirstName=null;
		if(firstName==null || !firstName.matches(regex)){
			hasError = true;
			errorStrFirstName="First Name invalid!";
		}
		//Address
		regex="[0-9]+?[\\s[a-zA-Z]]{1,}[\\s[a-zA-Z]\\x2E]?";
		errorStrAddress=null;
		if(address==null || !address.matches(regex)){
			hasError=true;
			errorStrAddress="Address invalid!";
		}
		//City
		regex = "[[A-Z][a-zA-z]][\\s[A-Z][a-zA-Z]]*";
		errorStrCity=null;
		if(city==null|| !city.matches(regex)){
			hasError=true;
			errorStrCity = "Error: Invalid City!";
		}
		
		//State
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
		
		
		//Id
		try{
			errorStrId=null;
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch(Exception e){
			hasError=true;
			errorStrId="Invalid Id!";
		}
		
		//Start Date
		try{			
			errorStrStartDate=null;
			startDate = Date.valueOf(request.getParameter("startDate"));
			//if(startDate.after(now))
			//(fix later)		
		}
		
		catch(Exception e){
			hasError=true;
			errorStrStartDate="Invalid Date!";
		}
		
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
			employee = new Employee(SSN, firstName, lastName, address, zipCode, telephone, id, startDate, hourlyRate );
			Location location = new Location(zipCode, city, state);
			try{
				ManagerUtils.addEmployee(conn, employee, location);
			}
			catch(SQLException e){
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		//set errorstring and employee then forward to views
		request.setAttribute("errorStrSSN", errorStrSSN);
		request.setAttribute("errorStrLastName", errorStrLastName);
		request.setAttribute("errorStrFirstName", errorStrFirstName);
		request.setAttribute("errorStrAddress", errorStrAddress);
		request.setAttribute("errorStrCity", errorStrCity);
		request.setAttribute("errorStrCity", errorStrCity);
		request.setAttribute("errorStrZipCode", errorStrZipCode);
		request.setAttribute("errorStrTelephone", errorStrTelephone);
		request.setAttribute("errorStrId", errorStrId);
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
		request.setAttribute("startDate", startDate);
		request.setAttribute("hourlyRate", hourlyRate);
		request.setAttribute("employee", employee);
		//If there's an error, stay on createEmployeeView
		if(hasError){
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