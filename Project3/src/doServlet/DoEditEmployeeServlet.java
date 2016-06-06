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
import utils.ManagerUtils;
import utils.MyUtils;

@WebServlet(urlPatterns = {"/doEditEmployee"})
public class DoEditEmployeeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public DoEditEmployeeServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		Connection conn = MyUtils.getStoredConnection(request);
		
		String SSN				= request.getParameter("SSN");		
		String firstName 		= request.getParameter("firstName");
		String lastName 		= request.getParameter("lastName");
		String address 			= request.getParameter("address");
		String zipCodeStr		= request.getParameter("zipCode");
		String telephone 		= request.getParameter("telephone");
		String id 				= request.getParameter("id");
		String startDate		=(request.getParameter("startDate"));
		String hourlyRateStr 	= request.getParameter("hourlyRate");
		
		boolean hasError=false;
		
		int zipCode=0;
		int hourlyRate=0;
		String errorStrLastName, errorStrFirstName, errorStrAddress, errorStrZipCode, errorStrTelephone,
			errorStrHourlyRate;

		Employee employee=null;
		
		// check for errors before adding an Employee to the database
		
		//LastName
		String regex=null;
		regex="[a-zA-Z]+";
		errorStrLastName=null;
		if(lastName==null || !lastName.matches(regex)){
			hasError=true;
			errorStrLastName="Last Name invalid!";
			}
		
		//FirstName
		errorStrFirstName=null;
		if(firstName==null || !firstName.matches(regex)){
			hasError=true;
			errorStrFirstName="First Name invalid!";
		}
		//Address
		regex="[0-9]+?[\\s[a-zA-Z]]{1,}[\\s[a-zA-Z]\\x2E]?";
		errorStrAddress=null;
		if(address==null ||!address.matches(regex)){
			hasError=true;
			errorStrAddress="Address invalid!";
		}
		//Zip Code
		try{
			errorStrZipCode=null;
			zipCode = Integer.parseInt(zipCodeStr);
			if(zipCode<0 || zipCode>99999){
				hasError=true;
				errorStrZipCode = "Invalid Zip Code";
			}		
		}catch(Exception  e){
			hasError = true;
			errorStrZipCode="Invalid Zip Code!";
		}
		
		// Telephone
		regex="[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
		errorStrTelephone=null;
			telephone = request.getParameter("telephone");
		if(telephone==null||!telephone.matches(regex)){
			hasError=true;
			errorStrTelephone="Invalid Telephone Number!";
		}
		
		//Id
		/*try{
			errorStrId=null;
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch(Exception e){
			hasError=true;
			errorStrId="Invalid Id!";
		}*/
		
		//Start Date
		/*try{			
			errorStrStartDate=null;
			startDate = Date.valueOf(request.getParameter("startDate"));
			//if(startDate.after(now))
			//(fix later)		
		}
		
		catch(Exception e){
			hasError=true;
			errorStrStartDate="Invalid Date!";
		}*/
		
		//Hourly Rate
		try{
			errorStrHourlyRate=null;
			hourlyRate = Integer.parseInt(hourlyRateStr);
			if(hourlyRate<0){
				errorStrHourlyRate="Invalid Hourly Rate";
			}
		}
		catch(Exception e){
			hasError=true;
			errorStrHourlyRate="Invalid Id!";
		}
		
		String errorString = null;
		

	
		
		//check if employee with ssn or id already exists
		/*try {
			employee = ManagerUtils.findEmployee(conn, id);
			if (employee!=null){
				hasError=true;
				errorStrId = "Error: Id already exists!";
			}
			employee = (Employee)ManagerUtils.findPerson(conn, SSN);
			if(employee!=null){
				hasError=true;
				errorStrSSN = "Error: SSN already exists!";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(!hasError){
			employee = new Employee(Integer.parseInt(SSN), firstName, lastName, address, zipCode, telephone, Integer.parseInt(id), Date.valueOf(startDate), hourlyRate );
			
			try{
				ManagerUtils.updateEmployee(conn, employee);
			}
			catch(SQLException e){
				e.printStackTrace();
				errorString = e.getMessage();
				System.out.println("Error occured");
			}
		}
		if(hasError){
			employee = new Employee();
			request.setAttribute("errorStrFirstName", errorStrFirstName);
			request.setAttribute("errorStrLastName", errorStrLastName);
			request.setAttribute("errorStrAddress", errorStrAddress);
			request.setAttribute("errorStrZipCode", errorStrZipCode);
			request.setAttribute("errorStrHourlyRate", errorStrHourlyRate);
			request.setAttribute("SSN", SSN);
			request.setAttribute("lastName", lastName);
			request.setAttribute("firstName", firstName);
			request.setAttribute("address", address);
			request.setAttribute("zipCode", zipCode);
			request.setAttribute("telephone", telephone);
			request.setAttribute("id", id);
			request.setAttribute("startDate", startDate);
			request.setAttribute("hourlyRate", hourlyRate);
			request.setAttribute("employee", employee);
		}

		// If there's an error, forward the information back to the edit page
		if(hasError){
			System.out.println("Not valid");
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/editEmployeeView.jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath() +"/employeeList");
		}
	}
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}