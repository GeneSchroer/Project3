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
import javax.servlet.http.HttpSession;

import beans.Client;
import beans.Location;
import beans.UserAccount;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/representatives/doEditClient"})
public class DoEditClientServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DoEditClientServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		String firstName		= request.getParameter("firstName");
		String lastName 		= request.getParameter("lastName"); 
		String address 			= request.getParameter("address");
		String city				= request.getParameter("city");
		String state			= request.getParameter("state");
		String zipCodeStr		= request.getParameter("zipCode");
		String telephone		= request.getParameter("telephone");
		String email 			= request.getParameter("email");
		String ratingStr		= request.getParameter("rating");
		String creditCardNumber = request.getParameter("creditCardNumber");
		String idStr			= request.getParameter("id");
		Integer zipCode = null;
		Integer rating = null;
		Integer id = null;
		Integer brokerId = null;
		
		boolean hasError=false;
		String errorStrLastName, errorStrFirstName, errorStrAddress, 
		errorStrCity, errorStrState, errorStrZipCode, errorStrTelephone,	
		errorStrEmail, errorStrRating, errorStrCreditCardNumber, errorStrId;

		Client client = null;
		
		// check for errors before updating the client		
		
		//LastName
		String regex, regex2;
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
		if(address==null || !address.matches(regex)){
			hasError=true;
			errorStrAddress="Address invalid!";
		}
		//City
		regex = "[[A-Z][a-zA-z]]+[\\s[A-Z][a-zA-Z]]*";	//regex = One or more words, 
															  	//each beginning with a capital letter 
		errorStrCity=null;
		if(city==null|| !city.matches(regex)){
			hasError=true;
			errorStrCity = "Error: Invalid City!";
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
		regex="[0-9]{10}";
		regex2="[0-9]{3}\\x2D[0-9]{3}\\x2D[0-9]{4}";
		errorStrTelephone=null;
			telephone = request.getParameter("telephone");
		if(telephone==null|| (!telephone.matches(regex)&&!telephone.matches(regex2) )){
			hasError=true;
			errorStrTelephone="Invalid Telephone Number!";
		}
		//Email
		regex="\\w+\\x40\\w+[\\x2E[a-z]+]+"; 
		//regex= series of letters and numbers,
		// followed by an ampersand @, 
		//followed by more letters and numbers
		//ending in a lowercase word (e.g.  .com)
		errorStrEmail=null;
		if(email==null||!email.matches(regex)){
			hasError=true;
			errorStrEmail="Error: Invalid email!";
		}
		//Rating
		try{
			errorStrRating=null;
			rating = Integer.parseInt(ratingStr);
		}
		catch(Exception e){
			hasError=true;
			errorStrRating="Invalid Rating!";
		}
		
		//Credit Card Number
		System.out.println(hasError);
		regex="[0-9]{16}";
		regex2="[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]"
				+"-[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]";
		errorStrCreditCardNumber=null;
		if(creditCardNumber == null || (!creditCardNumber.matches(regex)
				&& !creditCardNumber.matches(regex2) ) ){
			hasError=true;
			errorStrCreditCardNumber="Error: Invalid Credit Card!";
		}
		System.out.println(hasError);
		//Id
		try{
			errorStrId=null;
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch(Exception e){
			hasError=true;
			errorStrId="Invalid Id!";
		}
		
		System.out.println(hasError);

		
		
		String errorString = null;
		
		if(!hasError){
			HttpSession session = request.getSession();
			brokerId = ((UserAccount)session.getAttribute("loginedUser")).getId();
			client = new Client(id, firstName, lastName, address, zipCode, telephone, email, rating, creditCardNumber, brokerId);
			Location location = new Location(zipCode, city, state);
			try{
				RepresentativeUtils.updateClient(conn, client, location);
			}
			catch(SQLException e){
				e.printStackTrace();
				hasError = true;
			}
		}
		//If there's an error, stay on Edit Client page
		if(hasError){
			client = new Client();
			//set errorString and employee then forward to views
			request.setAttribute("errorStrLastName", errorStrLastName);
			request.setAttribute("errorStrFirstName", errorStrFirstName);
			request.setAttribute("errorStrAddress", errorStrAddress);
			request.setAttribute("errorStrCity", errorStrCity);
			request.setAttribute("errorStrZipCode", errorStrZipCode);
			request.setAttribute("errorStrTelephone", errorStrTelephone);
			request.setAttribute("errorStrEmail", errorStrEmail);
			request.setAttribute("errorStrRating", errorStrRating);	request.setAttribute("errorStrId", errorStrId);
			request.setAttribute("errorStrCreditCardNumber", errorStrCreditCardNumber);
							
			request.setAttribute("SSN", id);
			request.setAttribute("lastName", lastName);
			request.setAttribute("firstName", firstName);
			request.setAttribute("address", address);
			request.setAttribute("city", city);
			request.setAttribute("state", state);
			request.setAttribute("zipCode", zipCodeStr);
			request.setAttribute("telephone", telephone);
			request.setAttribute("email", email);
			request.setAttribute("rating", ratingStr);
			request.setAttribute("creditCardNumber", creditCardNumber);
			request.setAttribute("id", idStr);
			request.setAttribute("client", client);	
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/representatives/editClientView.jsp");
			dispatcher.forward(request, response);
		}
		
		//If everything worked, redirect to the employeeList
		else{
			response.sendRedirect(request.getContextPath() + "/representatives/clientList");
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
	
}