package doServlet;

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

@WebServlet(urlPatterns = {"/managers/doEditEmployee"})
public class DoEditEmployeeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public DoEditEmployeeServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		
		//get information from the form
		String SSN				= request.getParameter("SSN");		
		String firstName 		= request.getParameter("firstName");
		String lastName 		= request.getParameter("lastName");
		String address 			= request.getParameter("address");
		String city				= request.getParameter("city");
		String state			= request.getParameter("state");
		String zipCodeStr		= request.getParameter("zipCode");
		String telephone 		= request.getParameter("telephone");
		String id 				= request.getParameter("id");
		String startDate		=(request.getParameter("startDate"));
		String hourlyRateStr 	= request.getParameter("hourlyRate");
		
		boolean hasError=false;
		
		int zipCode=0;
		int hourlyRate=0;
		String errorStrLastName, errorStrFirstName, errorStrAddress, errorStrCity, errorStrState, errorStrZipCode, errorStrTelephone,
			errorStrHourlyRate;

		Employee employee=null;
		Location location=null;
		
		// check for errors before adding an Employee to the database
		
		//LastName
		String regex=null;
		String regex2=null;
		regex="[a-zA-Z]+[\\s[a-zA-z]]*";
		errorStrLastName=null;
		//throw error if not a valid name
		if(lastName==null || !lastName.matches(regex)){
			hasError=true;
			errorStrLastName="Last Name invalid!";
			}
		
		//FirstName
		regex = "[a-zA-Z]+";
		errorStrFirstName=null;
		// throw error if not a valid name
		if(firstName==null || !firstName.matches(regex)){
			hasError=true;
			errorStrFirstName="First Name invalid!";
		}
		//Address
		regex="[0-9]+?[\\s[a-zA-Z_0-9]]{1,}[\\s[a-zA-Z]\\x2E]?";
		errorStrAddress=null;
		// throw error if not a valid address
		//(eg. 123 Success street)
		if(address==null ||!address.matches(regex)){
			hasError=true;
			errorStrAddress="Address invalid!";
		}
		
		//City
				regex = "[[A-Z][a-zA-z]+][\\s[A-Z][a-zA-Z]]*";
				errorStrCity=null;
				//throw error if not a valid city
				//(eg. New York City, etc.)
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
		// throw error if not a valid number
		try{
			errorStrZipCode=null;
			zipCode = Integer.parseInt(zipCodeStr);
			// throw error if not a valid zipcode
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
		// throw error if not a valid telephone number
		errorStrTelephone=null;
			telephone = request.getParameter("telephone");
		if(telephone==null||(!telephone.matches(regex)&&!telephone.matches(regex2))){
			hasError=true;
			errorStrTelephone="Invalid Telephone Number!";
		}
		//Hourly Rate
		//throw error if not a number
		try{
			errorStrHourlyRate=null;
			hourlyRate = Integer.parseInt(hourlyRateStr);
			//throw error if rate is negative
			if(hourlyRate<0){
				hasError=true;
				errorStrHourlyRate="Invalid Hourly Rate";
			}
		}
		catch(Exception e){
			hasError=true;
			errorStrHourlyRate="Invalid Hourly Rate!";
		}
		
		String errorString = null;
		

	
		
		// if all went well, edit employee information
		if(!hasError){
			employee = new Employee(Integer.parseInt(SSN), firstName, lastName, address, zipCode, telephone, Integer.parseInt(id), Date.valueOf(startDate), hourlyRate );
			location = new Location(zipCode, city, state);
			try{
				ManagerUtils.updateEmployee(conn, employee, location);
			}
			catch(SQLException e){
				e.printStackTrace();
				errorString = e.getMessage();
				System.out.println("Error occured");
			}
		}
		//if an error occured, stay on Edit page and pass error message
		if(hasError){
			employee = new Employee();
			request.setAttribute("errorStrFirstName", errorStrFirstName);
			request.setAttribute("errorStrLastName", errorStrLastName);
			request.setAttribute("errorStrAddress", errorStrAddress);
			request.setAttribute("errorStrCity", errorStrCity);
			request.setAttribute("errorStrState", errorStrState);
			request.setAttribute("errorStrZipCode", errorStrZipCode);
			request.setAttribute("errorStrTelephone", errorStrTelephone);
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
		

		// If there's an error, forward the information back to the edit page
		
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/managers/editEmployeeView.jsp");
			dispatcher.forward(request, response);
		}
		// otherwise return to employee list
		else{
			response.sendRedirect(request.getContextPath() +"/managers/employeeList");
		}
	}
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}